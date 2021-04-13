package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Patient;
	
import com.mrsisa.mrsisaprojekat.model.Patient;

public interface PatientService {
	Collection<Patient> findAll();
	
	Patient findOne(String id);
	
	Patient create(Patient patient) throws Exception;
	
	Patient update(Patient patient) throws Exception;
	
	void delete(String id);
	
	Collection<Patient> findByNameAndLastName(String name, String lastName);
}
