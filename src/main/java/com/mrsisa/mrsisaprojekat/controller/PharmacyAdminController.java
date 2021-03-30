package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AdminPharmacyDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;

@RestController
@RequestMapping(value = "api/pharmacyAdmin")
public class PharmacyAdminController {
	
	@Autowired
	private PharmacyAdminService adminService;
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping(value="/all")
	public ResponseEntity<List<AdminPharmacyDTO>> getAdmins(){
		
		List<AdminPharmacy> admins = adminService.findAll();
		
		System.out.println("hhhhhh");
		List<AdminPharmacyDTO> adminsDTO = new ArrayList<>();
		for(AdminPharmacy a : admins) {
			adminsDTO.add(new AdminPharmacyDTO(a));
			
		}
		System.out.println("aaaaaaaa");
		return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AdminPharmacyDTO> getAdmin(@PathVariable String email){
		
		AdminPharmacy admin = adminService.findOne(email);
		
		if(admin == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new AdminPharmacyDTO(admin), HttpStatus.OK);
	} 
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<AdminPharmacyDTO> saveAdmin(@RequestBody AdminPharmacyDTO adminDTO) throws Exception{
		
		Address address = new Address();
		address.setCountry(adminDTO.getAddress().getCountry());
		address.setCity(adminDTO.getAddress().getCity());
		address.setStreet(adminDTO.getAddress().getStreet());
		address.setNumber(adminDTO.getAddress().getNumber());
		
		Address saved = addressService.create(address);
		
		AdminPharmacy admin = new AdminPharmacy();
		admin.setEmail(adminDTO.getEmail());
		admin.setPassword(adminDTO.getPassword());
		admin.setName(adminDTO.getName());
		admin.setLastName(adminDTO.getLastName());
		admin.setPhoneNumber(adminDTO.getPhoneNumber());
		admin.setPharmacy(null);
		admin.setAddress(saved);
		System.out.println(saved.getId());
		admin = adminService.create(admin);
		return new ResponseEntity<>(new AdminPharmacyDTO(admin), HttpStatus.CREATED); 
		
		
	}
	

}
