package com.mrsisa.mrsisaprojekat.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AdminSystemDTO;
import com.mrsisa.mrsisaprojekat.dto.PatientDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Category;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) throws Exception{
		
		Address address = new Address();
		address.setCountry(patientDTO.getAddress().getCountry());
		address.setCity(patientDTO.getAddress().getCity());
		address.setStreet(patientDTO.getAddress().getStreet());
		address.setNumber(patientDTO.getAddress().getNumber());
		
		Address saved = addressService.create(address);
		
		Patient patient = new Patient();
		patient.setEmail(patientDTO.getEmail());
		patient.setPassword(patientDTO.getPassword());
		patient.setName(patientDTO.getName());
		patient.setLastName(patientDTO.getLastName());
		patient.setPhoneNumber(patientDTO.getPhoneNumber());
		patient.setAddress(saved);
		patient.setCategory(Category.REGULAR);
		patient.setLoyaltyPoints(0);
		patient.setPenaltyPoints(0);
		patient = patientService.create(patient);
		
		try {
			emailService.sendTestMail(patient);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.CREATED); 
	}
	
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Patient>> searchPatients(@RequestParam String name, @RequestParam String lastName) {
		Collection<Patient> foundPatients = patientService.findByNameAndLastName(name, lastName);
		for (Patient p : foundPatients) {
			p.setAllergies(null);
			p.setAppointments(null);
			p.setComplaints(null);
			p.setePrescriptions(null);
			p.setSubscribedPharmacies(null);
		}
		
		if (foundPatients == null || foundPatients.isEmpty())
			return new ResponseEntity<Collection<Patient>>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Collection<Patient>>(foundPatients, HttpStatus.OK);
	}
}
