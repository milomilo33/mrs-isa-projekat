package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.mrsisa.mrsisaprojekat.dto.AdminPharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.PharmacistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;


@RestController
@RequestMapping(value ="/api/pharmacist")
public class PharmacistController {
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired 
	private AddressService addressService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping(value="/all")
	public ResponseEntity<List<PharmacistDTO>> getPharmacists(){
		
		List<Pharmacist> pharmacists = pharmacistService.findAll();
		
		//System.out.println(pharmacists.size());
		List<PharmacistDTO> pharmacistsDTO = new ArrayList<>();
		for(Pharmacist p : pharmacists) {
			pharmacistsDTO.add(new PharmacistDTO(p));
			
		}
		
		return new ResponseEntity<>(pharmacistsDTO, HttpStatus.OK);
	}

	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacistDTO> getPharmacist(@PathVariable("id") String email) {
		Pharmacist pharmacist = pharmacistService.findOne(email);

		if (pharmacist == null) {
			return new ResponseEntity<PharmacistDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<PharmacistDTO>(new PharmacistDTO(pharmacist), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<PharmacistDTO> savePharmacist(@RequestBody PharmacistDTO pharmacistDTO) throws Exception{
		System.out.println(pharmacistDTO.getPharmacy().getName());
		Address address = new Address();
		address.setCountry(pharmacistDTO.getAddress().getCountry());
		address.setCity(pharmacistDTO.getAddress().getCity());
		address.setStreet(pharmacistDTO.getAddress().getStreet());
		address.setNumber(pharmacistDTO.getAddress().getNumber());
		Address saved = addressService.create(address);
		System.out.println(saved.getStreet());
		Pharmacy pharmacy = pharmacyService.findOne(pharmacistDTO.getPharmacy().getId());
		
		Pharmacist pharmacist = new Pharmacist();
		pharmacist.setEmail(pharmacistDTO.getEmail());
		pharmacist.setPassword(pharmacistDTO.getPassword());
		pharmacist.setName(pharmacistDTO.getName());
		pharmacist.setLastName(pharmacistDTO.getLastName());
		pharmacist.setPhoneNumber(pharmacistDTO.getPhoneNumber());
		pharmacist.setWorkHourFrom(pharmacistDTO.getWorkHourFrom());
		pharmacist.setWorkHourTo(pharmacistDTO.getWorkHourTo());
		pharmacist.setCalendar(null);
		pharmacist.setCounselings(null);
		pharmacist.setDeleted(false);
		pharmacist.setRatings(null);
		pharmacist.setRequests(null);
		pharmacist.setPharmacy(pharmacy);
		pharmacist.setAddress(saved);
		System.out.println(saved.getId());
		pharmacist = pharmacistService.create(pharmacist);
		return new ResponseEntity<>(new PharmacistDTO(pharmacist), HttpStatus.CREATED); 
		
		
	}
	/*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pharmacist> updatePharmacist(@RequestBody Pharmacist pharmacist) throws Exception {
		Pharmacist pharmacistUpdate = pharmacistService.findOne(pharmacist.getEmail());


		if (pharmacistUpdate == null) {
			return new ResponseEntity<Pharmacist>(HttpStatus.NOT_FOUND);
		}
		
		pharmacistUpdate.setAddress(pharmacist.getAddress());
		pharmacistUpdate.setCalendar(pharmacist.getCalendar());
		pharmacistUpdate.setCounselings(pharmacist.getCounselings());
		pharmacistUpdate.setLastName(pharmacist.getLastName());
		pharmacistUpdate.setName(pharmacist.getName());
		pharmacistUpdate.setPassword(pharmacist.getPassword());
		pharmacistUpdate.setPhoneNumber(pharmacist.getPhoneNumber());
		pharmacistUpdate.setRatings(pharmacist.getRatings());
		pharmacistUpdate.setRequests(pharmacist.getRequests());
		pharmacistUpdate.setWorkHourFrom(pharmacist.getWorkHourFrom());
		pharmacistUpdate.setWorkHourTo(pharmacist.getWorkHourTo());

		pharmacistUpdate = pharmacistService.update(pharmacistUpdate);
		return new ResponseEntity<Pharmacist>(pharmacistUpdate, HttpStatus.OK);
	}*/

	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePharmacist(@PathVariable("id") String email) {

		Pharmacist pharmacist = pharmacistService.findOne(email);
		
		if (pharmacist != null) {
			System.out.println("Yes");
			pharmacistService.delete(email);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
