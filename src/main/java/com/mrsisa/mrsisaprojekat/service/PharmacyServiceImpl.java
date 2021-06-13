package com.mrsisa.mrsisaprojekat.service;

import java.util.*;

import com.mrsisa.mrsisaprojekat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;

@Service

public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicamentItemService medicamentService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Override
	public List<Pharmacy> findAll() {
		List<Pharmacy> pharmacies = pharmacyRepository.getAllWithAddress();
		return pharmacies;
	}

	@Override
	public Pharmacy findOne(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOnePharmacy(id);
		return pharmacy;
	}

	@Override
	public Pharmacy create(Pharmacy pharmacy) throws Exception {
		if(pharmacy.getId() != null) {
			return null;
		}
		Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
		return savedPharmacy;
	}
	
	@Override
	public Pharmacy update(Pharmacy pharmacy) throws Exception {
		Pharmacy pharmacyToUpdate = pharmacyRepository.findById(pharmacy.getId()).orElseGet(null);
		if (pharmacyToUpdate == null) {
			return null;
		}
		pharmacyRepository.save(pharmacy);
		return pharmacy;
	}

	@Override
	public void delete(Long id) {
		pharmacyRepository.deleteById(id);
		
	}

	@Override
	public List<MedicamentItem> getAllMedicaments(Long id) {
		List<MedicamentItem> items = pharmacyRepository.getAllMedicaments(id);
		if(items == null) {
			return null;
		}
		return items;
	}

	@Transactional(readOnly = false)
	@Override
	public Pharmacy findOneWithMedicaments(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithMedicaments(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}
	
	@Override
	public Pharmacy findOneWithDermatologists(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithDermatologists(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}

	@Override
	public Pharmacy findOneWithPharmacists(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithPharmacists(id);
		if (pharmacy == null) {
			return null;
		}

		return pharmacy;
	}

	@Override
	public int getRating(Long id) {
		List<Rating> ratings = pharmacyRepository.getRatings(id);
		int i = 0;
		int sum = 0;

		for(Rating r : ratings) {
			sum += r.getValue();
			i++;
		}
		if(i == 0) return 0;

		return Math.round(sum / i);
	}

	@Override
	public Pharmacy findOneWithAppointments(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithAppointments(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}
	
	@Override
	public ArrayList<Appointment> findAllAppointmentsDeramtologist(String email, Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithAppointments(id);
		if (pharmacy == null) {
			return null;
		}
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		for(Appointment a : pharmacy.getAppointments()) {
			if(a.getChosenEmployee().getEmail().equals(email)) {
				appointments.add(a);
			}
		}
	
		return appointments;
	}

	@Override
	public void addRating(Rating rating, Long id) {
		Pharmacy pharmacy = pharmacyRepository.loadWithRatingOfUser(id, rating.getPatient().getEmail());

		Patient p = patientService.getPatientExaminationMedicationDone(rating.getPatient().getEmail());

		if (pharmacy == null) {
			pharmacy = pharmacyRepository.loadWithRatings(id);

			try {
				pharmacy.getRatings().add(rating);
			} catch (NullPointerException e) {
				HashSet<Rating> ratings = new HashSet<>();
				ratings.add(rating);
				pharmacy.setRatings(ratings);
			}
		} else {
			pharmacy.getRatings().stream().findFirst().ifPresent(r -> r.setValue(rating.getValue()));
		}

		ArrayList<Employee> employees = new ArrayList<>(pharmacy.getDermatologists());
		employees.addAll(pharmacy.getPharmacists());

		pharmacyRepository.save(pharmacy);
	}

	@Override
	public Integer getRatingOfUser(Long pharmacyId, String patientEmail) {
		Pharmacy pharmacy = pharmacyRepository.loadWithRatingOfUser(pharmacyId, patientEmail);

		if(pharmacy == null) return 0;

		return Objects.requireNonNull(pharmacy.getRatings().stream().findFirst().orElse(null)).getValue();
	}

	@Override
	public ArrayList<Appointment> findAvailableAppointmentsDeramtologist(String email, Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithAppointments(id);
		if (pharmacy == null) {
			return null;
		}
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		for(Appointment a : pharmacy.getAppointments()) {
			if(a.getChosenEmployee().getEmail().equals(email) && a.getPatient() == null) {
				
				a.setChosenEmployee(null);
				System.out.println("DA");

				appointments.add(a);
			}
		}
	
		return appointments;
	}

	@Override
	public Set<Pharmacy> getAllWithMedicaments() {
		return pharmacyRepository.getAllWithMedicaments();
  }
  @Override
	public Pharmacy findOneWithRequests(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithRequests(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}

@Override
public Set<Pharmacy> findAllWithAdmin() {
	return pharmacyRepository.getAllWithAdmins();
}

@Override
public void deletePharmacy(Long id) throws Exception {
	Pharmacy pharmacy = this.findOneWithAppointments(id);
	pharmacy.setDeleted(true);
	pharmacyRepository.save(pharmacy);
	
	Collection<MedicamentItem> medicaments = this.getAllMedicaments(pharmacy.getId());
	for(MedicamentItem mi : medicaments) {
		medicamentService.deleteMedicament(mi);
	}
	
	Collection<Appointment> appointments = pharmacy.getAppointments();
	for(Appointment a : appointments) {
		appointmentService.delete(a);
	}
	
	Set<AdminPharmacy> admins = pharmacyAdminService.getAllAdminsInPharmacy(pharmacy.getId());
	for(AdminPharmacy ap : admins) {
		ap.setPharmacy(null);
		pharmacyAdminService.update(ap);
	}
	
}

@Override
public ArrayList<Appointment> findAvailableAppointmentsPharmacist(String email, Long id) {
	Pharmacy pharmacy = pharmacyRepository.getOneWithAppointments(id);
	if (pharmacy == null) {
		return null;
	}
	ArrayList<Appointment> appointments = new ArrayList<Appointment>();
	for(Appointment a : pharmacy.getAppointments()) {
		if(a.getChosenEmployee().getEmail().equals(email) && a.getPatient() == null) {
			a.setChosenEmployee(null);
			appointments.add(a);
		}
	}

	return appointments;
}




}
