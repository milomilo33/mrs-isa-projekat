package com.mrsisa.mrsisaprojekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrsisa.mrsisaprojekat.model.Patient;

public interface PatientRepositoryDB extends JpaRepository<Patient, String>{

}
