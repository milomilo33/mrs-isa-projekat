package com.mrsisa.mrsisaprojekat.controller;

import java.util.*;

import com.mrsisa.mrsisaprojekat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;
import com.mrsisa.mrsisaprojekat.model.WorkHour.Day;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PharmacistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;
import com.mrsisa.mrsisaprojekat.service.SupplierService;
import com.mrsisa.mrsisaprojekat.service.SystemAdminService;
import com.mrsisa.mrsisaprojekat.service.WorkHourService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/api/pharmacist")
public class PharmacistController {
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private SystemAdminService systemAdminService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired 
	private AddressService addressService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private WorkHourService workHourService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping(value="/all")
	public ResponseEntity<List<PharmacistDTO>> getPharmacists(){
		
		List<Pharmacist> pharmacists = pharmacistService.findAll();
		
		//System.out.println(pharmacists.size());
		List<PharmacistDTO> pharmacistsDTO = new ArrayList<>();
		for(Pharmacist p : pharmacists) {
			ArrayList<WorkHourDTO> hours = new ArrayList<WorkHourDTO>();
			for(WorkHour h : p.getWorkHour()) {
				WorkHourDTO wd = new WorkHourDTO(h);
				hours.add(wd);
			}
			PharmacistDTO pd = new PharmacistDTO(p);
			pd.setWorkHours(hours);
			pharmacistsDTO.add(pd);
			
		}
		
		return new ResponseEntity<>(pharmacistsDTO, HttpStatus.OK);
	}

	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'SYSTEM_ADMIN', 'PHARMACY_ADMIN', 'PATIENT')")
	public ResponseEntity<PharmacistDTO> getPharmacist(@PathVariable("id") String email) {
		Pharmacist pharmacist = pharmacistService.findOne(email);

		if (pharmacist == null) {
			return new ResponseEntity<PharmacistDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<PharmacistDTO>(new PharmacistDTO(pharmacist), HttpStatus.OK);
	}
	
	boolean check(String email) {
		Pharmacist pharmacist = pharmacistService.findOne(email);

		if (pharmacist != null) {
			return true;
		}
		Dermatologist dermatologist = dermatologistService.findOne(email);
		if (dermatologist != null) {
			return true;
		}
		AdminPharmacy pharmacyAdmin = pharmacyAdminService.findOne(email);
		if (pharmacyAdmin != null) {
			return true;
		}
		/*AdminSystem sytemAdmin = systemAdminService.findOne(email);
		if (sytemAdmin != null) {
			return true;
		}
		/*Supplier supplier = supplierService.findOne(email);
		if (supplier != null) {
			return true;
		}*/
		
		return false;
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'SYSTEM_ADMIN', 'PHARMACY_ADMIN')")
	public ResponseEntity<PharmacistDTO> savePharmacist(@RequestBody PharmacistDTO pharmacistDTO) throws Exception{
		
		if(check(pharmacistDTO.getEmail())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		Address address = new Address();
		address.setCountry(pharmacistDTO.getAddress().getCountry());
		address.setCity(pharmacistDTO.getAddress().getCity());
		address.setStreet(pharmacistDTO.getAddress().getStreet());
		address.setNumber(pharmacistDTO.getAddress().getNumber());
		Address saved = addressService.create(address);
		
		Pharmacy pharmacy = pharmacyService.findOne(pharmacistDTO.getPharmacy().getId());
		
		Set<WorkHour> workHours = new HashSet<WorkHour>();
		
		for(WorkHourDTO w: pharmacistDTO.getWorkHours()){
			WorkHour v = new WorkHour();
			v.setId(w.getId());
			v.setDeleted(false);
			v.setWorkHourFrom(w.getWorkHourFrom());
			v.setWorkHourTo(w.getWorkHourTo());
			v.setPharmacy(pharmacy);
			if(w.getDay().equals("Monday")) {
				v.setDay(Day.MONDAY);
			}else if(w.getDay().equals("Tuesday")) {
				v.setDay(Day.TUESDAY);
			}else if(w.getDay().equals("Wednesday")) {
				v.setDay(Day.WEDNESDAY);
			}else if(w.getDay().equals("Thursday")) {
				v.setDay(Day.THURSDAY);
			}else if(w.getDay().equals("Friday")) {
				v.setDay(Day.FRIDAY);
			}else if(w.getDay().equals("Friday")) {
				v.setDay(Day.FRIDAY);
			}else if(w.getDay().equals("Saturday")) {
				v.setDay(Day.SATURDAY);
			}else if(w.getDay().equals("Sunday")) {
				v.setDay(Day.SUNDAY);
			}
			WorkHour savedW = workHourService.create(v);
			workHours.add(savedW);
		}
		
		Pharmacist pharmacist = new Pharmacist();
		pharmacist.setEmail(pharmacistDTO.getEmail());
		pharmacist.setPassword(pharmacistDTO.getPassword());
		pharmacist.setName(pharmacistDTO.getName());
		pharmacist.setLastName(pharmacistDTO.getLastName());
		pharmacist.setPhoneNumber(pharmacistDTO.getPhoneNumber());
		pharmacist.setWorkHour(workHours);
		pharmacist.setCalendar(null);
		pharmacist.setCounselings(null);
		pharmacist.setDeleted(false);
		pharmacist.setRatings(null);
		pharmacist.setRequests(null);
		pharmacist.setActive(true);
		pharmacist.setPharmacy(pharmacy);
		pharmacist.setAddress(saved);
		pharmacist = pharmacistService.create(pharmacist);
		
		try {
			emailService.sendTestMail(pharmacist);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(new PharmacistDTO(pharmacist), HttpStatus.CREATED); 
		
		
	}

	@Transactional(readOnly = false)
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'PHARMACIST')")
	public ResponseEntity<PharmacistDTO> deletePharmacist(@PathVariable("id") String email) {

		Pharmacist pharmacist = pharmacistService.findOne(email);
		if (pharmacist != null) {
			try {
				if(pharmacist.getCounselings().size()== 0) {	
					for(WorkHour w : pharmacist.getWorkHour()) {
						workHourService.delete(w.getId());
							
					}
					return new ResponseEntity<>(new PharmacistDTO(pharmacist),HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
			
				
			}catch(NullPointerException e) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/counselings/done/patient")
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	// svi gotovi pregledi (sa pacijentima) za trenutno ulogovanog farmaceuta
	public ResponseEntity<Collection<Appointment>> getAllDoneCounselingsWithPatientsForPharmacist() {
		Pharmacist currentPharmacist = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Appointment> doneCounselings = pharmacistService.getDoneCounselingsWithPatientsForPharmacist(currentPharmacist.getEmail());

		if (doneCounselings == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if (doneCounselings.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Appointment>>(doneCounselings, HttpStatus.OK);
	}
	
	@GetMapping(value="/ratings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public double getRatings(@PathVariable("id") String email) {
	
		Pharmacist p = pharmacistService.getRatings(email);
		try {
			p.getRatings();
		}catch(NullPointerException e) {
			return 0;
		}
		
		
		double val = 0;
		
		for(Rating g : p.getRatings()) {
			val+=g.getValue();
		}
		val= val/p.getRatings().size();
		return val;
	}
}
