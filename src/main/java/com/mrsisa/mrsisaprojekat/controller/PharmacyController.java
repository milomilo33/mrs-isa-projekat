package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
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
@RequestMapping(value="/api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PharmacyDTO>> getPharmacies(){
		
		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for(Pharmacy p : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(p));

		}

		return new ResponseEntity<Collection<PharmacyDTO>>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyDTO> getPharmacy(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOne(id);

		if (pharmacy == null) {
			return new ResponseEntity<PharmacyDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PharmacyDTO>(new PharmacyDTO(pharmacy), HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) throws Exception {
		Address address = addressService.create(pharmacy.getAddress());
		pharmacy.setAddress(address);
		Pharmacy savedPharmacy = pharmacyService.create(pharmacy);
		return new ResponseEntity<Pharmacy>(savedPharmacy, HttpStatus.CREATED);
	}
}
