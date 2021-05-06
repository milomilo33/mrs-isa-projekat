package com.mrsisa.mrsisaprojekat.repository;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Dermatologist;
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



}