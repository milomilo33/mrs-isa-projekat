package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Patient;

public interface PatientRepositoryDB extends JpaRepository<Patient, String>{
	@Query("select p from Patient p join fetch p.address where p.deleted = false")
	List<Patient> getAllWithAddress();
}
