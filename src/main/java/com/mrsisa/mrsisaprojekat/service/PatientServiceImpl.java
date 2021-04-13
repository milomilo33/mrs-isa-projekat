package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientRepositoryDB patientRepository;
	
	@Override
	//@Transactional
	public Collection<Patient> findByNameAndLastName(String name, String lastName) {
		return patientRepository.findByNameAndLastName(name, lastName);
	}

}
