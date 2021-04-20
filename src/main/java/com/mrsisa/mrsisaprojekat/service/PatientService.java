package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Patient;
	
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;

import java.util.Collection;

public interface PatientService {
	Collection<Patient> findAll();

	Patient findOne(String id);

	Patient create(Patient patient) throws Exception;

	Patient update(Patient patient) throws Exception;

	void delete(String id);

	Collection<Patient> findByNameAndLastName(String name, String lastName);

	Patient getOneWithReservedMeds(String email);

	Long updateWithReservation(Patient p, PrescriptionMedicament medicamentToReserve);

	Collection<Appointment> getUpcomingAppointmentsForUser(String email, Appointment.AppointmentType type);

	Patient getOneWithAppointments(String email);

	Long updateWithAppointment(Patient patient, Appointment appointment);

}