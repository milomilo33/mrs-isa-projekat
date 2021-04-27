package com.mrsisa.mrsisaprojekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.SupplierDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Supplier;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.SupplierService;

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
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'PHARMACY_ADMIN', 'SUPPLIER')")
	public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO supplierDTO) throws Exception{
		
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
		
		supplier.setActive(true);
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
	
	

}
