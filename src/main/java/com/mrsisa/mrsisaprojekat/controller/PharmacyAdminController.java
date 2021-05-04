package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AdminPharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.RequestMedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.RequestMedicament;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import com.mrsisa.mrsisaprojekat.service.PharmacistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;
import com.mrsisa.mrsisaprojekat.service.SystemAdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/pharmacyAdmin")
public class PharmacyAdminController {
	
	@Autowired
	private PharmacyAdminService adminService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SystemAdminService sysAdminService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private PharmacistService pharmacistService;
	@Autowired
	private PharmacyService pharmacyService;
	
	
	@Autowired
	private PatientService patientService;

	@GetMapping(value="/all")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<List<AdminPharmacyDTO>> getAdmins(){
		
		List<AdminPharmacy> admins = adminService.findAll();
		
		List<AdminPharmacyDTO> adminsDTO = new ArrayList<>();
		for(AdminPharmacy a : admins) {
			adminsDTO.add(new AdminPharmacyDTO(a));
			
		}
		return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN')")
	public ResponseEntity<AdminPharmacyDTO> getAdmin(@PathVariable String id){
		AdminPharmacy admin = adminService.findOne(id);
		if(admin == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new AdminPharmacyDTO(admin), HttpStatus.OK);
	} 
	
	@PostMapping(consumes = "application/json")
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN')")
	public ResponseEntity<AdminPharmacyDTO> saveAdmin(@RequestBody AdminPharmacyDTO adminDTO) throws Exception{
		
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
		
		Pharmacy pharmacy = pharmacyService.findOne(adminDTO.getPharmacy().getId());
		
		
		AdminPharmacy admin = new AdminPharmacy();
		admin.setEmail(adminDTO.getEmail());
		admin.setPassword(adminDTO.getPassword());
		admin.setName(adminDTO.getName());
		admin.setLastName(adminDTO.getLastName());
		admin.setPhoneNumber(adminDTO.getPhoneNumber());
		admin.setPharmacy(pharmacy);
		admin.setActive(true);
		admin.setAddress(saved);
		AdminPharmacy adminSaved = new AdminPharmacy();
		adminSaved = adminService.create(admin);
		
		try {
			emailService.sendTestMail(admin);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(new AdminPharmacyDTO(adminSaved), HttpStatus.CREATED); 
		
		
	}
	
	@PutMapping(value= "/updatePharmacyAdmin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'DERMATOLOGIST')")
	public ResponseEntity<AdminPharmacyDTO> updatePharmacyAdmin(@RequestBody AdminPharmacyDTO admin,@PathVariable("id") String email) throws Exception {
		AdminPharmacy adminUpdate = adminService.findOne(email);
		adminUpdate.setPhoneNumber(admin.getPhoneNumber());
		adminUpdate.setEmail(admin.getEmail());
		adminUpdate.setName(admin.getName());
		adminUpdate.setLastName(admin.getLastName());
		if(adminUpdate.getAddress().getCountry().equals(admin.getAddress().getCountry()) &&
				adminUpdate.getAddress().getCity().equals(admin.getAddress().getCity()) &&
				adminUpdate.getAddress().getStreet().equals(admin.getAddress().getStreet()) &&
				adminUpdate.getAddress().getNumber() == admin.getAddress().getNumber()) {
			Address ad =  addressService.findOne(adminUpdate.getAddress().getId());
			adminUpdate.setAddress(ad);
		}else {
			Address ad = new Address();
			ad.setCountry(admin.getAddress().getCountry());
			ad.setCity(admin.getAddress().getCity());
			ad.setStreet(admin.getAddress().getStreet());
			ad.setNumber(admin.getAddress().getNumber());
			
			Address saved = addressService.create(ad);
			adminUpdate.setAddress(saved);
		}
		
		adminUpdate = adminService.update(adminUpdate);
		return new ResponseEntity<AdminPharmacyDTO>(new AdminPharmacyDTO(adminUpdate), HttpStatus.OK);
	}
	
	@GetMapping(value = "/requestMedicaments/{id}")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<AdminPharmacyDTO> getRequestMedicaments(@PathVariable("id") String email) {
		AdminPharmacy admin = adminService.findOneWithRequestMedicaments(email);
		ArrayList<RequestMedicamentDTO> requests = new ArrayList<RequestMedicamentDTO>();
		for(RequestMedicament m : admin.getRequestMedicaments()) {
			requests.add(new RequestMedicamentDTO(m));
		}
		AdminPharmacyDTO a = new AdminPharmacyDTO(admin);
		a.setRequestMedicaments(requests);

		return new ResponseEntity<AdminPharmacyDTO>(a, HttpStatus.OK);
	}
	

}
