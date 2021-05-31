package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.UserDTO;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Supplier;
import com.mrsisa.mrsisaprojekat.model.User;
import com.mrsisa.mrsisaprojekat.service.AppointmentService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;
import com.mrsisa.mrsisaprojekat.service.SupplierService;
import com.mrsisa.mrsisaprojekat.service.SystemAdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value= "/api/users")
public class UserController {
	
	@Autowired
	private SystemAdminService adminSystemService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping(value="/getUsers/{email}")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<Collection<UserDTO>> getAllUsers(@PathVariable("email") String email){
		Collection<AdminSystem> systemAdmins = adminSystemService.findAll();
		Collection<AdminPharmacy> pharmacyAdmins = pharmacyAdminService.findAll();
		Collection<Supplier> suppliers = supplierService.findAll();
		Collection<Dermatologist> dermatologists = dermatologistService.findAll();
		Collection<UserDTO> users = new ArrayList<UserDTO>();
		
		for(AdminSystem a : systemAdmins) {
			if(!a.getEmail().equalsIgnoreCase(email)) {
				UserDTO dto = new UserDTO(a.getName(), a.getLastName(), a.getEmail(), "System Admin", a.getPhoneNumber());
				users.add(dto);
			}
		}
		for(AdminPharmacy a : pharmacyAdmins) {
			UserDTO dto = new UserDTO(a.getName(), a.getLastName(), a.getEmail(), "Pharmacy Admin", a.getPhoneNumber());
			users.add(dto);
		}
		for(Supplier s : suppliers) {
			UserDTO dto = new UserDTO(s.getName(), s.getLastName(), s.getEmail(), "Supplier", s.getPhoneNumber());
			users.add(dto);
		}
		for(Dermatologist d : dermatologists) {
			UserDTO dto = new UserDTO(d.getName(), d.getLastName(), d.getEmail(), "Dermatologist", d.getPhoneNumber());
			users.add(dto);
		}
		
		return new ResponseEntity<Collection<UserDTO>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value="/deleteUser/{email}")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	@Transactional
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("email") String email){
		
		UserDTO deleted = new UserDTO();
		User user;
		Dermatologist dermatologist = dermatologistService.findOne(email);
		try {
			if(dermatologist != null) {
				dermatologistService.delete(email);
				deleted = new UserDTO(dermatologist.getName(), dermatologist.getLastName(), dermatologist.getEmail(), "Dermatologist", dermatologist.getPhoneNumber());
				for(Appointment a : dermatologist.getMedicalExaminations()) {
					appointmentService.delete(a);
				}
				return new ResponseEntity<UserDTO>(deleted, HttpStatus.OK);
			}
		}
		catch(NullPointerException e) {
			
		}
		try {
			user = supplierService.findOne(email);
			if(user != null) {
				supplierService.delete(email);
				deleted = new UserDTO(user.getName(), user.getLastName(), user.getEmail(), "Supplier", user.getPhoneNumber());
			
				return new ResponseEntity<UserDTO>(deleted, HttpStatus.OK);
			}
		} 
		catch(NullPointerException e) {
			
		}
		try {
			user = adminSystemService.findOne(email);
			if(user != null) {
				adminSystemService.delete(email);
				deleted = new UserDTO(user.getName(), user.getLastName(), user.getEmail(), "System Admin", user.getPhoneNumber());
			
				return new ResponseEntity<UserDTO>(deleted, HttpStatus.OK);
			}
		} 
		catch(NullPointerException e) {
			
		}
		try {
			user = pharmacyAdminService.findOne(email);
			if(user != null) {
				pharmacyAdminService.delete(email);
				deleted = new UserDTO(user.getName(), user.getLastName(), user.getEmail(), "Pharmacy Admin", user.getPhoneNumber());
			
				return new ResponseEntity<UserDTO>(deleted, HttpStatus.OK);
			}
		}
		catch(NullPointerException e) {
			
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
