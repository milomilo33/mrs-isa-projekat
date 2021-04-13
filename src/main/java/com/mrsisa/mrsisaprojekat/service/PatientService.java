package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Patient;

public interface PatientService {
	Collection<Patient> findByNameAndLastName(String name, String lastName);
}
