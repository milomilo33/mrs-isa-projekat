package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mrsisa.mrsisaprojekat.dto.ListPharmacyDTO;
import com.mrsisa.mrsisaprojekat.service.PharmacyServiceImpl;
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

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ListPharmacyDTO>> getPharmacies(){
		Collection<Pharmacy> pharmacies = pharmacyService.findAll();

		Collection<ListPharmacyDTO> pharmaciesDTO = new ArrayList<>();

		for(Pharmacy p : pharmacies) {
			pharmaciesDTO.add(new ListPharmacyDTO(p));
		}
		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);

	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListPharmacyDTO> getPharmacy(@PathVariable("id") Long id) {
		System.out.println(id);
		Pharmacy pharmacy = pharmacyService.findOne(id);

		ListPharmacyDTO pharmacyDTO = new ListPharmacyDTO(pharmacy);
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) throws Exception {
		Address a = addressService.create(pharmacy.getAddress());
		pharmacy.setAddress(a);
		Pharmacy savedPharmacy = pharmacyService.create(pharmacy);
		return new ResponseEntity<Pharmacy>(savedPharmacy, HttpStatus.CREATED);
	}
}
