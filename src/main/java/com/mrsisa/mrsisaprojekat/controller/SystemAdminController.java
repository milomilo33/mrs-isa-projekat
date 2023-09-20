package com.mrsisa.mrsisaprojekat.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AdminSystemDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.PatientDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import com.mrsisa.mrsisaprojekat.service.PharmacistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;
import com.mrsisa.mrsisaprojekat.service.SystemAdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/systemAdmin")
public class SystemAdminController {
	
	@Autowired
	private SystemAdminService sysAdminService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyAdminService adminService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private PharmacistService pharmacistService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping(value="/{email}")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<AdminSystemDTO> getAdmin(@PathVariable("email") String email){
		AdminSystem admin = sysAdminService.findOne(email);
		if(admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new AdminSystemDTO(admin), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<AdminSystemDTO> saveAdmin(@RequestBody AdminSystemDTO adminDTO) throws Exception{
		
		try {
			AdminPharmacy savedAdmin = adminService.findOne(adminDTO.getEmail());
			if(savedAdmin != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Patient patient = patientService.findOne(adminDTO.getEmail());
			if(patient != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			AdminSystem adminsystem = sysAdminService.findOne(adminDTO.getEmail());
			if(adminsystem != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Dermatologist dermatologist = dermatologistService.findOne(adminDTO.getEmail());
			if(dermatologist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Pharmacist pharmacist = pharmacistService.findOne(adminDTO.getEmail());
			if(pharmacist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(NullPointerException e) {
			
		}
		
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
		admin.setActive(false);
		admin = sysAdminService.create(admin);
		
		try {
			emailService.sendTestMail(admin);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(new AdminSystemDTO(admin), HttpStatus.CREATED); 
		
		
	}
	@PutMapping(value= "/changePassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<AdminSystemDTO> changePassword(@RequestBody AdminSystemDTO admin,@PathVariable("id") String password) throws Exception {
		AdminSystem adminUpdate = sysAdminService.findOne(admin.getEmail());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				admin.getEmail(), password));
		
		adminUpdate.setPassword(passwordEncoder.encode(admin.getPassword()));
		if(!adminUpdate.isActive()) {
			adminUpdate.setActive(true);
		}
		
		adminUpdate = sysAdminService.update(adminUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PostMapping(value = "/update", consumes = "application/json")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	@Transactional
	public ResponseEntity<AdminSystemDTO> updateDermatologist(@RequestBody AdminSystemDTO adminSystemDTO) throws Exception {
		AdminSystem adminSystem = sysAdminService.findOne(adminSystemDTO.getEmail());
		if (adminSystem != null) {
			Address a = addressService.findOne(adminSystemDTO.getAddress().getId());

			a.setCountry(adminSystemDTO.getAddress().getCountry());
			a.setCity(adminSystemDTO.getAddress().getCity());
			a.setStreet(adminSystemDTO.getAddress().getStreet());
			a.setNumber(adminSystemDTO.getAddress().getNumber());
			a = addressService.update(a);
			adminSystem.setName(adminSystemDTO.getName());
			adminSystem.setLastName(adminSystemDTO.getLastName());
			adminSystem.setPhoneNumber(adminSystemDTO.getPhoneNumber());

			adminSystem = sysAdminService.update(adminSystem);

			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
