package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.SubscribedPharmacyDTO;
import com.mrsisa.mrsisaprojekat.exceptions.ReservationQuantityException;
import com.mrsisa.mrsisaprojekat.model.*;

import java.util.Collection;

public interface PatientService {
	Collection<Patient> findAll();

	Collection<Pharmacy> filterPharmacy(String user, int rating);

	Patient findOne(String id);

	Patient create(Patient patient) throws Exception;

	Patient update(Patient patient) throws Exception;

	void delete(String id);

	Collection<Patient> findByNameAndLastName(String name, String lastName);

	Patient getOneWithReservedMeds(String email);

	Patient getOneWithReservedMedsAndePrescriptions(String email);

	PrescriptionMedicament updateWithReservation(Patient p, PrescriptionMedicament medicamentToReserve) throws Exception;

	PrescriptionMedicament updateWithPrescription(String patientEmail, PrescriptionMedicament medicamentToReserve, Long medicalReportId) throws Exception;

	Collection<Appointment> getUpcomingAppointmentsForUser(String email, Appointment.AppointmentType type);

	Patient getOneWithAppointments(String email);

	Long updateWithAppointment(Patient patient, Appointment appointment);

	Patient getPatientDetails(String email);

	void checkMedicamentReservationQuantity(PrescriptionMedicament medicament, Long pharmacyId) throws ReservationQuantityException;

	boolean checkMedicamentReservationQuantityForPrescription(PrescriptionMedicament medicament, Long pharmacyId, Employee employee) throws ReservationQuantityException;

	Collection<Pharmacy> findAllSubscribed(String user);

    void addAllergy(String patientEmail, Long medicamentId) throws Exception;

    Patient getPatientAllergies(String email);

	void removeAllergy(String patientEmail, Long medicamentId);

	Patient getOneWithePrescriptions(String email);

	Patient getOneOnlyePrescription(String email);

	Patient findAllWithPurchasedMedicamentsAndAppointments(String email);

	Patient getOneWithAddress(String email);
	
	Patient getOneWithCategory(String email);

	boolean checkIfTermFilled(Patient patient, Appointment appointment);

    Patient getMedicamentIfPurchased(String email, Long id);

	Patient getPatientExaminationIfDone(String email, String ratedEmployeeEmail);

	Patient getPatientExaminationMedicationDone(String email);

	Patient findOneWithLock(String email);

	SubscribedPharmacyDTO unsubsribe(SubscribedPharmacyDTO pharmacyDTO);
}