package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.repository.DermatologistRepositoryDB;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DermatologistServiceImpl implements DermatologistService {

	@Autowired
	private DermatologistRepositoryDB dermatologistRepository;
	
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
		Dermatologist savedDermatologist = dermatologistRepository.save(dermatologist);
		return savedDermatologist;
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
			if (!a.isDeleted() && !a.isDone()) {
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


}
