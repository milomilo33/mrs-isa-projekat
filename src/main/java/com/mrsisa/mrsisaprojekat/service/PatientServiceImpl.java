package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PrescriptionRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Role;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

	@Autowired
	private PatientRepositoryDB patientRepository;

	@Autowired
	private PrescriptionRepositoryDB prescriptionRepository;

	@Autowired
	private AppointmentRepositoryDB appointmentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Override
	public Collection<Patient> findAll() {
		List<Patient> patients = patientRepository.getAllWithAddress();
		return patients;
	}

	@Override
	public Patient findOne(String id) {
		Patient patient = patientRepository.findById(id).orElseGet(null);
		return patient;
	}

	@Override
	public Patient create(Patient patient) throws Exception {

		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_PATIENT");
		patient.setRoles(roles);
		return patientRepository.save(patient);
	}

	@Override
	public Patient update(Patient patient) throws Exception {
		Patient patientToUpdate = patientRepository.findById(patient.getEmail()).orElseGet(null);
		if (patientToUpdate == null) {
			return null;
		}
		
		return patientRepository.save(patient);
	}

	@Override
	public Patient getOneWithReservedMeds(String email) {
		Patient p = patientRepository.getPatientWithReservedMedicaments(email);
		System.out.println(p);
		if(p == null) {
			p = findOne(email);
			p.setReservedMedicaments(new HashSet<>());
		}
		return p;
	}

	@Override
	public Long updateWithReservation(Patient p, PrescriptionMedicament medicamentToReserve) {
		Patient patientToUpdate = patientRepository.getPatientWithReservedMedicaments(p.getEmail());

		if (patientToUpdate == null) {
			patientToUpdate = patientRepository.findById(p.getEmail()).orElse(null);
			patientToUpdate.setReservedMedicaments(new HashSet<>());
			if(patientToUpdate == null) return null;
		}

		patientToUpdate.getReservedMedicaments().add(medicamentToReserve);
		PrescriptionMedicament pm = prescriptionRepository.save(medicamentToReserve);
		patientRepository.save(p);

		return pm.getId();
	}

	@Override
	public void delete(String id) {
		patientRepository.deleteById(id);
	}

	
	@Override
	//@Transactional
	public Collection<Patient> findByNameAndLastName(String name, String lastName) {
		return patientRepository.findByNameAndLastName(name, lastName);
	}

	@Override
	public Collection<Appointment> getUpcomingAppointmentsForUser(String userEmail, Appointment.AppointmentType type) {
//		List<Appointment> appointments = patientRepository.getAppointmentsForUser(userEmail);
//
//		List<Appointment> upcomingAppointments = new ArrayList<Appointment>();
//		for (Appointment a : appointments) {
//			LocalDate appointmentDate = a.getDate();
//			LocalDate today = LocalDate.now();
//			if (today.isBefore(appointmentDate) || today.isEqual(appointmentDate)) {
//				if (a.getType() == type) {
//					a.setMedicalReport(null);
//					upcomingAppointments.add(a);
//				}
//			}

	//	}

		return null;
	}

	@Override
	public Patient getOneWithAppointments(String email) {
		Patient p = patientRepository.getAppointmentsForUser(email);

		if(p == null) {
			p = findOne(email);
			p.setAppointments(new HashSet<>());
		}
		return p;
	}

	@Override
	public Long updateWithAppointment(Patient patient, Appointment appointment) {
		patient.getAppointments().add(appointment);
		Appointment a = appointmentRepository.save(appointment);
		patientRepository.save(patient);

		return a.getId();
	}

}
