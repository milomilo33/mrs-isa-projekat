package com.mrsisa.mrsisaprojekat.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AdminSystemDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.SystemAdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/systemAdmin")
public class SystemAdminController {
	
	@Autowired
	private SystemAdminService adminService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<AdminSystemDTO> saveAdmin(@RequestBody AdminSystemDTO adminDTO) throws Exception{
		
		Address address = new Address();
		address.setCountry(adminDTO.getAddress().getCountry());
		address.setCity(adminDTO.getAddress().getCity());
		address.setStreet(adminDTO.getAddress().getStreet());
		address.setNumber(adminDTO.getAddress().getNumber());
		
		Address saved = addressService.create(address);
		
		AdminSystem admin = new AdminSystem();
		admin.setEmail(adminDTO.getEmail());
		admin.setPassword(adminDTO.getPassword());
		admin.setName(adminDTO.getName());
		admin.setLastName(adminDTO.getLastName());
		admin.setPhoneNumber(adminDTO.getPhoneNumber());
		admin.setAddress(saved);
		admin = adminService.create(admin);
		
		try {
			emailService.sendTestMail(admin);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(new AdminSystemDTO(admin), HttpStatus.CREATED); 
		
		
	}

}
