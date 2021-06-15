package com.mrsisa.mrsisaprojekat.repository;

import com.mrsisa.mrsisaprojekat.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Patient;

import java.util.Collection;
import java.util.List;

public interface PatientRepositoryDB extends JpaRepository<Patient, String> {

	@Query("select p from Patient p join fetch p.address where p.deleted = false")
	List<Patient> getAllWithAddress();
	
	@Query("select p "
		 + "from Patient p "
		 + "join fetch p.address "
		 + "where lower(p.name) like lower(concat('%', concat(?1, '%'))) and lower(p.lastName) like lower(concat('%', concat(?2, '%')))")
	List<Patient> findByNameAndLastName(String name, String lastName);

	@Query("select p from Patient p join fetch p.reservedMedicaments prm where p.email=?1 and prm.deleted = false and prm.purchased = false")
	Patient getPatientWithReservedMedicaments(String email);

	@Query("select p from Patient p join fetch p.roles where p.email=?1")
	Patient getOneLogin(String id);

	@Query("select p from Patient p join fetch p.appointments pa where p.email = ?1 and pa.deleted = false")
	Patient getAppointmentsForUser(String email);

	@Query("select p.subscribedPharmacies from Patient p where p.email=?1")
	Collection<Pharmacy> findAllSubscribedPharmacies(String user);

	@Query("select p from Patient p left join fetch p.ePrescriptions pep join fetch pep.prescriptionMedicaments where p.email=?1 and pep.done = false")
	Patient getPatientWithePrescriptions(String email);
	
	@Query("select p from Patient p join fetch p.ePrescriptions pep join fetch pep.prescriptionMedicaments join fetch p.appointments pa join fetch pa.chosenEmployee join fetch pep.pharmacy join fetch p.complaints where p.email=?1 and pep.done = true and pa.deleted = false")
	Patient getPatientWithPurchasedMedicaments(String email);

	@Query("select p from Patient p join fetch p.allergies pa where p.email = ?1")
	Patient getPatientWithAllergies(String patientEmail);

	@Query("select p from Patient p join fetch p.address where p.email = ?1")
    Patient getOneWithAddress(String email);

	@Query("select p from Patient p join fetch p.reservedMedicaments prm where p.email = ?1 and prm.id = ?2 and prm.purchased = true")
	Patient getPatientPurchasedMedication(String email, Long id);

	@Query("select p from Patient p join fetch p.appointments pa where p.email = ?1 and pa.chosenEmployee.email = ?2 and pa.done = true")
	Patient getPatientDoneExamination(String email, String ratedEmployeeEmail);

	@Query("select p from Patient p join fetch p.appointments pa join fetch p.reservedMedicaments prm where p.email = ?1 and pa.done = true and prm.purchased = true")
	Patient getPatientExaminationMedicationDone(String email);

	@Query("select p from Patient p left join fetch p.ePrescriptions pep where p.email = ?1")
	Patient getOneOnlyePrescription(String email);
}