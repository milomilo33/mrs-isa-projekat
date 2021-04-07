package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.EmailService;

@RestController
@RequestMapping(value="/api/dermatologist")
public class DermatologistController {

	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	

	@GetMapping(value="/all")
	public ResponseEntity<List<DermatologistDTO>> getDermatologist(){
		
		List<Dermatologist> dermatologist = dermatologistService.findAll();
		
		List<DermatologistDTO> dermatologistDTO = new ArrayList<>();
		for(Dermatologist d : dermatologist) {
			
			DermatologistDTO pd = new DermatologistDTO(d);
			dermatologistDTO.add(pd);
			
		}
		
		return new ResponseEntity<>(dermatologistDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistDTO> getDermatologist(@PathVariable("id") String email) {
		Dermatologist dermatologist = dermatologistService.findOne(email);

		if (dermatologist == null) {
			return new ResponseEntity<DermatologistDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<DermatologistDTO>(new DermatologistDTO(dermatologist), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<DermatologistDTO> saveDermatologist(@RequestBody DermatologistDTO dermatologistDTO) throws Exception{
		
		Address address = new Address();
		address.setCountry(dermatologistDTO.getAddress().getCountry());
		address.setCity(dermatologistDTO.getAddress().getCity());
		address.setStreet(dermatologistDTO.getAddress().getStreet());
		address.setNumber(dermatologistDTO.getAddress().getNumber());
		Address saved = addressService.create(address);
		
		
		Dermatologist dermatologist = new Dermatologist();
		dermatologist.setEmail(dermatologistDTO.getEmail());
		dermatologist.setPassword(dermatologistDTO.getPassword());
		dermatologist.setName(dermatologistDTO.getName());
		dermatologist.setLastName(dermatologistDTO.getLastName());
		dermatologist.setPhoneNumber(dermatologistDTO.getPhoneNumber());
		
		dermatologist.setCalendar(null);
		dermatologist.setActive(false);
		dermatologist.setMedicalExaminations(null);
		dermatologist.setDeleted(false);
		dermatologist.setRatings(null);
		dermatologist.setRequests(null);
		dermatologist.setPharmacy(null);
		dermatologist.setAddress(saved);
		dermatologist = dermatologistService.create(dermatologist);
		
		try {
			emailService.sendTestMail(dermatologist);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(new DermatologistDTO(dermatologist), HttpStatus.CREATED); 
		
		
	}
}

