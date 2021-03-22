package com.mrsisa.mrsisaprojekat.controller;

import java.util.Collection;

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

import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Pharmacy>> getPharmacies(){
		
		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		
		return new ResponseEntity<Collection<Pharmacy>>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pharmacy> getPharmacy(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOne(id);

		if (pharmacy == null) {
			return new ResponseEntity<Pharmacy>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Pharmacy>(pharmacy, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) throws Exception {
		Pharmacy savedPharmacy = pharmacyService.create(pharmacy);
		return new ResponseEntity<Pharmacy>(savedPharmacy, HttpStatus.CREATED);
	}
}
