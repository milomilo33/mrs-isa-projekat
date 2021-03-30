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

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.service.AddressService;

@RestController
@RequestMapping(value= "/api/address")
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Address>> getAddress(){
		
		Collection<Address> address = addressService.findAll();
		
		return new ResponseEntity<Collection<Address>>(address, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> getAddress(@PathVariable("id") Long id) {
		Address address = addressService.findOne(id);

		if (address == null) {
			return new ResponseEntity<Address>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> createAddress(@RequestBody Address address) throws Exception {
		Address savedAddress = addressService.create(address);
		return new ResponseEntity<Address>(savedAddress, HttpStatus.CREATED);
	}

}
