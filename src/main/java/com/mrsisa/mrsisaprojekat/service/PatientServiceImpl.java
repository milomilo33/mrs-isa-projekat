package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;

@Service
public class PatientServiceImpl implements PatientService{

	@Autowired
	private PatientRepositoryDB patientRepository;
	
	@Override
	public Collection<Patient> findAll() {
		List<Patient> patients = patientRepository.getAllWithAddress();
		return patients;
	}

	@Override
	public Patient findOne(String id) {
		Patient patient = patientRepository.findById(id).orElseGet(null);
		return patient;
	}

	@Override
	public Patient create(Patient patient) throws Exception {
		return patientRepository.save(patient);
	}

	@Override
	public Patient update(Patient patient) throws Exception {
		Patient patientToUpdate = patientRepository.findById(patient.getEmail()).orElseGet(null);
		if (patientToUpdate == null) {
			return null;
		}
		
		return patientRepository.save(patient);
	}

	@Override
	public void delete(String id) {
		patientRepository.deleteById(id);
	}

	
	@Override
	//@Transactional
	public Collection<Patient> findByNameAndLastName(String name, String lastName) {
		return patientRepository.findByNameAndLastName(name, lastName);
	}
	
}
