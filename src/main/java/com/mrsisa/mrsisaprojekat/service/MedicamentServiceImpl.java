package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.MedicamentDTO;
import com.mrsisa.mrsisaprojekat.exceptions.RatingException;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.MedicamentRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class MedicamentServiceImpl implements MedicamentService {

	@Autowired
	private MedicamentRepositoryDB medicamentRepository;

	@Autowired
	private PatientService patientService;

	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private AppointmentService appointmentService;
	
	@Override
	public Medicament getRatings(Long id) {
		return medicamentRepository.getRatings(id);
	}
	@Override
	public Collection<Medicament> findAll() {
		return medicamentRepository.findAll();
	}

	@Override
	public Medicament findOne(Long id) {
		return medicamentRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Medicament create(Medicament medicament) {
		return medicamentRepository.save(medicament);
	}

	@Override
	public Medicament update(Medicament medicament) {
		Medicament temp = medicamentRepository.findById(medicament.getId()).orElse(null);
		if(temp == null) {
			return null;
		}
		temp.setDeleted(false);
		temp.setAnnotations(medicament.getAnnotations());
		temp.setIssuanceMode(medicament.getIssuanceMode());
		temp.setManufacturer(medicament.getManufacturer());
		temp.setMedicamentForm(medicament.getMedicamentForm());
		temp.setName(medicament.getName());
		temp.setStructure(medicament.getStructure());
		temp.setRatings(medicament.getRatings());
		temp.setSubstituteMedicaments(medicament.getSubstituteMedicaments());
		temp.setType(medicament.getType());
		
		return medicamentRepository.save(temp);
	}

	@Override
	public void delete(Medicament medicament) {
		medicament.setDeleted(true);
		medicamentRepository.save(medicament);
		
	}
	@Override
	public Collection<Medicament> findAllWithName(String query) {
		return medicamentRepository.findAllWithName(query);
	}
	@Override
	public Collection<Medicament> findAllFilter(int mode, int form) {
		return medicamentRepository.findFilter(mode, form);
	}

	@Override
	@Transactional
	public void addRating(Rating rating, Long id) throws RatingException {
		Medicament medicament = medicamentRepository.loadWithRatingOfUser(id, rating.getPatient().getEmail());
		Patient p = patientService.getMedicamentIfPurchased(rating.getPatient().getEmail(), id);

		if(p.getReservedMedicaments() == null) {
			throw new RatingException("You have never purchased this medicament. You can't rate it.");
		}

		if(medicament == null) {
			medicament = medicamentRepository.loadWithRatings(id);
			try {
				medicament.getRatings().add(rating);
			} catch (NullPointerException e) {
				HashSet<Rating> ratings = new HashSet<>();
				ratings.add(rating);
				medicament.setRatings(ratings);
			}
		}
		else {
			medicament.getRatings().stream().findFirst().ifPresent(r -> r.setValue(rating.getValue()));
		}

		medicamentRepository.save(medicament);
	}

	@Override
	public Integer getRatingOfUser(Long medicamentId, String patientEmail) {
		Medicament medicament = medicamentRepository.loadWithRatingOfUser(medicamentId, patientEmail);

		if(medicament == null) return 0;

		return Objects.requireNonNull(medicament.getRatings().stream().findFirst().orElse(null)).getValue();
	}
	@Transactional(readOnly = true)
	public Collection<MedicamentDTO> getNonallergicSubstituteMedicinesForPatientInPharmacyWithQuantity(Long medicamentId, String patientEmail, int quantity, Long pharmacyId) {
		Medicament medicament = this.findOne(medicamentId);
		Patient patient = patientService.findOne(patientEmail);
		Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);

		if (medicament == null || patient == null || pharmacy == null) {
			return null;
		}

		if (medicament.isDeleted() || patient.isDeleted() || pharmacy.isDeleted()) {
			return null;
		}

		Collection<Medicament> substituteMedicaments = medicament.getSubstituteMedicaments();
		Collection<MedicamentDTO> filteredSubstitutes = new HashSet<>();
		for (Medicament substitute : substituteMedicaments) {
			MedicamentItem medicamentInPharmacy = null;
			for (MedicamentItem item : pharmacy.getMedicamentItems()) {
				if (item.isDeleted()) {
					continue;
				}
				if (item.getId().equals(substitute.getId())) {
					if (item.getQuantity() - quantity >= 0) {
						medicamentInPharmacy = item;
					}
					break;
				}
			}

			if (medicamentInPharmacy == null) {
				continue;
			}

			boolean allergic = false;
			for (Medicament allergyMed : patient.getAllergies()) {
				if (allergyMed.isDeleted()) {
					continue;
				}
				if (substitute.getId().equals(allergyMed.getId())) {
					allergic = true;
					break;
				}
			}

			if (allergic) {
				continue;
			}

			MedicamentDTO dto = new MedicamentDTO(substitute);
			filteredSubstitutes.add(dto);
		}

		return filteredSubstitutes;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<MedicamentDTO> getNonallergicMedicinesForPatientInPharmacyOfAppointment(String patientEmail, Long appointmentId) {
		Patient patient = patientService.findOne(patientEmail);
		Appointment appointment = appointmentService.findOne(appointmentId);

		if (patient == null || appointment == null) {
			return null;
		}

		if (patient.isDeleted() || appointment.isDeleted()) {
			return null;
		}

		Pharmacy pharmacy = appointment.getMedicalReport().getEprescription().getPharmacy();

		if (pharmacy == null) {
			return null;
		}

		if (pharmacy.isDeleted()) {
			return null;
		}

		Collection<MedicamentItem> pharmacyMedicamentItems = pharmacy.getMedicamentItems();

		if (pharmacyMedicamentItems == null) {
			return new HashSet<>();
		}

		Collection<MedicamentDTO> dtos = new HashSet<>();
		for (MedicamentItem mi : pharmacyMedicamentItems) {
			if (mi.isDeleted()) {
				continue;
			}

			boolean allergic = false;
			for (Medicament allergyMed : patient.getAllergies()) {
				if (allergyMed.isDeleted()) {
					continue;
				}
				if (allergyMed.getId().equals(mi.getMedicament().getId())) {
					allergic = true;
					break;
				}
			}

			if (allergic) {
				continue;
			}

			MedicamentDTO dto = new MedicamentDTO(mi.getMedicament());
			dtos.add(dto);
		}

		return dtos;
	}
	@Override
	public void deleteMedicament(Long id) throws Exception {
		Medicament med = this.findOne(id);
		med.setDeleted(true);
		medicamentRepository.save(med);
		
		Collection<Pharmacy> pharmacies = pharmacyService.getAllWithMedicaments();
		
		for(Pharmacy p : pharmacies) {
			Set<MedicamentItem> items = p.getMedicamentItems();
			for(MedicamentItem mi : items) {
				if(mi.getMedicament().getId() == id) {
					mi.setDeleted(true);
					break;
				}
			}
			pharmacyService.update(p);
		}
		
	}

}
