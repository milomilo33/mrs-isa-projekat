package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.DermatologistRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class DermatologistServiceImpl implements DermatologistService {

	@Autowired
	private DermatologistRepositoryDB dermatologistRepository;
	
	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;

	@Autowired
	private AppointmentRepositoryDB appointmentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	@Override
	public List<Dermatologist> findAll() {
		List<Dermatologist> dermatologists = dermatologistRepository.getAllWithAddress();
		return dermatologists;
	}

	@Override
	public Dermatologist findOne(String email) {
		Dermatologist dermatologist = dermatologistRepository.getOneDermatologist(email);
		return dermatologist;
	}

	@Override
	public Dermatologist create(Dermatologist dermatologist) throws Exception {
		dermatologist.setPassword(passwordEncoder.encode(dermatologist.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_DERMATOLOGIST");
		dermatologist.setRoles(roles);
		return dermatologistRepository.save(dermatologist);
	}

	@Override
	public Dermatologist update(Dermatologist dermatologist) throws Exception {
		Dermatologist dermatologistToUpdate = dermatologistRepository.findById(dermatologist.getEmail()).orElseGet(null);
		if (dermatologistToUpdate == null) {
			return null;
		}
		dermatologistRepository.save(dermatologist);
		return dermatologist;
	}

	@Override
	public boolean delete(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getUpcomingExaminationsForDermatologist(String email) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return null;
		}

		Collection<Appointment> examinations = dermatologist.getMedicalExaminations();

		Collection<Appointment> upcomingAppointments = new ArrayList<Appointment>();
		for (Appointment a : examinations) {
			LocalDate appointmentDate = a.getDate();
			LocalDate today = LocalDate.now();
			if (!a.isDeleted() && !a.isDone() && a.getPatient() != null) {
				if (today.isBefore(appointmentDate) || today.isEqual(appointmentDate)) {
					a.setMedicalReport(null);
					a.setChosenEmployee(null);
					a.getPatient().setSubscribedPharmacies(null);
					a.getPatient().setAllergies(null);
					a.getPatient().setAppointments(null);
					a.getPatient().setComplaints(null);
					a.getPatient().setePrescriptions(null);
					a.getPatient().setReservedMedicaments(null);
					a.setPatient((Patient) Hibernate.unproxy(a.getPatient()));
					upcomingAppointments.add(a);
				}
			}
		}

		return upcomingAppointments;
	}

	@Override
	@Transactional
	public boolean dermatologistHasAppointment(String email, Long appointmentId) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return false;
		}

		for (Appointment a : dermatologist.getMedicalExaminations()) {
			if (a.getId().equals(appointmentId)) {
				return true;
			}
		}

		return false;
	}


	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getDoneExaminationsWithPatientsForDermatologist(String email) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return null;
		}

		Collection<Appointment> examinations = dermatologist.getMedicalExaminations();
		Collection<Appointment> doneExaminations = new ArrayList<Appointment>();
		if (examinations == null) {
			return doneExaminations;
		}

		for (Appointment a : examinations) {
			if (a.isDone()) {
				a.setMedicalReport(null);
				a.setChosenEmployee(null);
				a.getPatient().setSubscribedPharmacies(null);
				a.getPatient().setAllergies(null);
				a.getPatient().setAppointments(null);
				a.getPatient().setComplaints(null);
				a.getPatient().setePrescriptions(null);
				a.getPatient().setReservedMedicaments(null);
				a.setPatient((Patient) Hibernate.unproxy(a.getPatient()));
				doneExaminations.add(a);
			}
		}

		return doneExaminations;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getAllExistingExaminationsForDermatologist(String email) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return null;
		}

		Collection<Appointment> examinations = dermatologist.getMedicalExaminations();
		Collection<Appointment> existingExaminations = new ArrayList<Appointment>();
		if (examinations == null) {
			return existingExaminations;
		}

		LocalDate today = LocalDate.now();
		for (Appointment a : examinations) {
			LocalDate appointmentDate = a.getDate();
			if (a.getPatient() == null) {
				boolean isBeforeScheduledTime = false;
				LocalTime timeFrom = a.getTermFrom();
				LocalTime now = LocalTime.now();
				isBeforeScheduledTime = now.isBefore(timeFrom);
				if (today.isBefore(appointmentDate) || (today.isEqual(appointmentDate) && isBeforeScheduledTime)) {
					a.setMedicalReport(null);
					a.setChosenEmployee(null);
					existingExaminations.add(a);
				}
			}
		}

		return existingExaminations;
	}

	@Override
	public Dermatologist getRatings(String email) {
		return dermatologistRepository.getRatings(email);
	}

	@Override
	public void addRating(Rating rating, String ratedEmployeeEmail) {
		Dermatologist dermatologist = dermatologistRepository.loadWithRatingOfUser(ratedEmployeeEmail, rating.getPatient().getEmail());

		if (dermatologist == null) {
			dermatologist = dermatologistRepository.getRatings(ratedEmployeeEmail);
			try {
				dermatologist.getRatings().add(rating);
			} catch (NullPointerException e) {
				HashSet<Rating> ratings = new HashSet<>();
				ratings.add(rating);
				dermatologist.setRatings(ratings);
			}
		} else {
			dermatologist.getRatings().stream().findFirst().ifPresent(r -> r.setValue(rating.getValue()));
		}

		dermatologistRepository.save(dermatologist);

	}

	@Override
	public Integer getRatingOfUser(String dermatologistEmail, String patientEmail) {
		Dermatologist dermatologist = dermatologistRepository.loadWithRatingOfUser(dermatologistEmail, patientEmail);

		if (dermatologist == null) return 0;

		return Objects.requireNonNull(dermatologist.getRatings().stream().findFirst().orElse(null)).getValue();
	}

	@Override
	public Dermatologist getOneWithAddress(String email) {
		Dermatologist dermatologist = dermatologistRepository.getOneWithAddress(email);
		if(dermatologist == null) {
			return null;
		}
		return dermatologist;
	}

	@Override
	public Dermatologist findOneExaminations(String email) {
		Dermatologist d = dermatologistRepository.getDermatologistWithExaminations(email);

		return d;
	}
}
