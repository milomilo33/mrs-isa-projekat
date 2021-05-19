package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.AppointmentCalendarDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.model.WorkHour.Day;
import com.mrsisa.mrsisaprojekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
	private SystemAdminService sysAdminService;
	
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyAdminService adminService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
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
		AdminSystem sytemAdmin = systemAdminService.findOne(email);
		if (sytemAdmin != null) {
			return true;
		}
		Supplier supplier = supplierService.findOne(email);
		if (supplier != null) {
			return true;
		}
		
		return false;
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'SYSTEM_ADMIN', 'PHARMACY_ADMIN')")
	public ResponseEntity<PharmacistDTO> savePharmacist(@RequestBody PharmacistDTO pharmacistDTO) throws Exception{
		

		try {
			AdminPharmacy savedAdmin = adminService.findOne(pharmacistDTO.getEmail());
			if(savedAdmin != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Patient patient = patientService.findOne(pharmacistDTO.getEmail());
			if(patient != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			AdminSystem adminsystem = sysAdminService.findOne(pharmacistDTO.getEmail());
			if(adminsystem != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Dermatologist dermatologist = dermatologistService.findOne(pharmacistDTO.getEmail());
			if(dermatologist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Pharmacist pharmacist = pharmacistService.findOne(pharmacistDTO.getEmail());
			if(pharmacist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(NullPointerException e) {
			
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
		pharmacist.setActive(false);
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
	
	@PutMapping(value= "/changePassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<PharmacistDTO> changePassword(@RequestBody PharmacistDTO pharmacist,@PathVariable("id") String password) throws Exception {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				pharmacist.getEmail(), password));
		Pharmacist pharmacistUpdate = pharmacistService.getOneWithAddress(pharmacist.getEmail());
		pharmacistUpdate.setPassword(passwordEncoder.encode(pharmacist.getPassword()));
		if(!pharmacistUpdate.isActive()) {
			pharmacistUpdate.setActive(true);
		}
		
		pharmacistUpdate = pharmacistService.update(pharmacistUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/appointments/calendar")
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<Collection<AppointmentCalendarDTO>> getAllAppointmentsBetweenDatesForCalendar(@RequestParam String startDateStr, @RequestParam String endDateStr) {
		LocalDateTime startDate = LocalDateTime.parse(startDateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		LocalDateTime endDate = LocalDateTime.parse(endDateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		Pharmacist currentPharmacist = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<AppointmentCalendarDTO> appointments = pharmacistService.getAllAppointmentsBetweenDatesForCalendar(startDate, endDate, currentPharmacist.getEmail());

		if (appointments == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (appointments.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(appointments, HttpStatus.OK);
	}
}
