package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.exceptions.ReservationQuantityException;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
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

	Long updateWithReservation(Patient p, PrescriptionMedicament medicamentToReserve) throws Exception;

	Collection<Appointment> getUpcomingAppointmentsForUser(String email, Appointment.AppointmentType type);

	Patient getOneWithAppointments(String email);

	Long updateWithAppointment(Patient patient, Appointment appointment);

	Patient getPatientDetails(String email);

	void checkMedicamentReservationQuantity(PrescriptionMedicament medicament) throws ReservationQuantityException;

	Collection<Pharmacy> findAllSubscribed(String user);

}