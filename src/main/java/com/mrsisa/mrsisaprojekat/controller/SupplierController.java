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

import com.mrsisa.mrsisaprojekat.dto.AdminPharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.AdminSystemDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.SupplierDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Supplier;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import com.mrsisa.mrsisaprojekat.service.PharmacistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;
import com.mrsisa.mrsisaprojekat.service.SupplierService;
import com.mrsisa.mrsisaprojekat.service.SystemAdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyAdminService adminService;
	
	@Autowired
	private SystemAdminService sysAdminService;
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DermatologistService dermatologistService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping(value="/{email}")
	@PreAuthorize("hasAnyRole('SUPPLIER')")
	public ResponseEntity<SupplierDTO> getSupplier(@PathVariable("email") String email){
		Supplier supplier = supplierService.findOne(email);
		if(supplier == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SupplierDTO(supplier), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'PHARMACY_ADMIN', 'SUPPLIER')")
	public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO supplierDTO) throws Exception{
		
		try {
			AdminPharmacy savedAdmin = adminService.findOne(supplierDTO.getEmail());
			if(savedAdmin != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Patient patient = patientService.findOne(supplierDTO.getEmail());
			if(patient != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			AdminSystem adminsystem = sysAdminService.findOne(supplierDTO.getEmail());
			if(adminsystem != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Dermatologist dermatologist = dermatologistService.findOne(supplierDTO.getEmail());
			if(dermatologist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Pharmacist pharmacist = pharmacistService.findOne(supplierDTO.getEmail());
			if(pharmacist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(NullPointerException e) {
			
		}
		Address address = new Address();
		address.setCountry(supplierDTO.getAddress().getCountry());
		address.setCity(supplierDTO.getAddress().getCity());
		address.setStreet(supplierDTO.getAddress().getStreet());
		address.setNumber(supplierDTO.getAddress().getNumber());
		Address saved = addressService.create(address);
		
		
		Supplier supplier = new Supplier();
		supplier.setEmail(supplierDTO.getEmail());
		supplier.setPassword(supplierDTO.getPassword());
		supplier.setName(supplierDTO.getName());
		supplier.setLastName(supplierDTO.getLastName());
		supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
		
		supplier.setActive(false);
		supplier.setDeleted(false);
		supplier.setAddress(saved);
		supplier = supplierService.create(supplier);
		
		try {
			emailService.sendTestMail(supplier);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(new SupplierDTO(supplier), HttpStatus.CREATED); 
		
		
	}
	@PutMapping(value= "/changePassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('SUPPLIER')")
	public ResponseEntity<SupplierDTO> changePassword(@RequestBody SupplierDTO supplier,@PathVariable("id") String password) throws Exception {
		Supplier supplierUpdate = supplierService.findOne(supplier.getEmail());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				supplier.getEmail(), password));
		supplierUpdate.setPassword(passwordEncoder.encode(supplier.getPassword()));
		if(!supplierUpdate.isActive()) {
			supplierUpdate.setActive(true);
		}
		
		supplierUpdate = supplierService.update(supplierUpdate);
		return new ResponseEntity<SupplierDTO>(new SupplierDTO(supplierUpdate), HttpStatus.OK);
	}
	
	@PostMapping(value = "/update", consumes = "application/json")
	@PreAuthorize("hasAnyRole('SUPPLIER')")
	@Transactional
	public ResponseEntity<DermatologistDTO> updateSupplier(@RequestBody SupplierDTO supplierDTO) throws Exception {
		Supplier supplier = supplierService.findOne(supplierDTO.getEmail());
		if (supplier != null) {
			Address a = addressService.findOne(supplierDTO.getAddress().getId());

			a.setCountry(supplierDTO.getAddress().getCountry());
			a.setCity(supplierDTO.getAddress().getCity());
			a.setStreet(supplierDTO.getAddress().getStreet());
			a.setNumber(supplierDTO.getAddress().getNumber());
			a = addressService.update(a);
			supplier.setName(supplierDTO.getName());
			supplier.setLastName(supplierDTO.getLastName());
			supplier.setPhoneNumber(supplierDTO.getPhoneNumber());

			supplier = supplierService.update(supplier);

			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

}
