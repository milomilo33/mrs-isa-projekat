package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.AppointmentCalendarDTO;
import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/dermatologist")
public class DermatologistController {

	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private WorkHourService workHourService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PharmacyAdminService adminService;
	
	@Autowired
	private SystemAdminService sysAdminService;
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	

	@GetMapping(value="/all")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'PHARMACY_ADMIN')")
	public ResponseEntity<List<DermatologistDTO>> getDermatologist(){
		
		List<Dermatologist> dermatologist = dermatologistService.findAll();
		List<DermatologistDTO> dermatologistDTO = new ArrayList<>();
		for(Dermatologist d : dermatologist) {
			DermatologistDTO pd = new DermatologistDTO(d);
			dermatologistDTO.add(pd);
			
		}
		
		return new ResponseEntity<>(dermatologistDTO,HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'PHARMACY_ADMIN', 'DERMATOLOGIST')")
	public ResponseEntity<DermatologistDTO> getDermatologist(@PathVariable("id") String email) {
		Dermatologist dermatologist = dermatologistService.findOne(email);

		if (dermatologist == null) {
			return new ResponseEntity<DermatologistDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<DermatologistDTO>(new DermatologistDTO(dermatologist), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'PHARMACY_ADMIN', 'DERMATOLOGIST')")
	public ResponseEntity<DermatologistDTO> saveDermatologist(@RequestBody DermatologistDTO dermatologistDTO) throws Exception{
		
		try {
			AdminPharmacy savedAdmin = adminService.findOne(dermatologistDTO.getEmail());
			if(savedAdmin != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Patient patient = patientService.findOne(dermatologistDTO.getEmail());
			if(patient != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			AdminSystem adminsystem = sysAdminService.findOne(dermatologistDTO.getEmail());
			if(adminsystem != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Dermatologist dermatologist = dermatologistService.findOne(dermatologistDTO.getEmail());
			if(dermatologist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Pharmacist pharmacist = pharmacistService.findOne(dermatologistDTO.getEmail());
			if(pharmacist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(NullPointerException e) {
			
		}
		Address address = new Address();
		address.setCountry(dermatologistDTO.getAddress().getCountry());
		address.setCity(dermatologistDTO.getAddress().getCity());
		address.setStreet(dermatologistDTO.getAddress().getStreet());
		address.setNumber(dermatologistDTO.getAddress().getNumber());
		Address saved = addressService.create(address);
		
		
		Dermatologist dermatologist = new Dermatologist();
		dermatologist.setEmail(dermatologistDTO.getEmail());
		dermatologist.setPassword(dermatologistDTO.getPassword());
		dermatologist.setName(dermatologistDTO.getName());
		dermatologist.setLastName(dermatologistDTO.getLastName());
		dermatologist.setPhoneNumber(dermatologistDTO.getPhoneNumber());
		
		dermatologist.setCalendar(null);
		dermatologist.setActive(false);
		dermatologist.setMedicalExaminations(null);
		dermatologist.setDeleted(false);
		dermatologist.setRatings(null);
		dermatologist.setRequests(null);
		dermatologist.setPharmacy(null);
		dermatologist.setAddress(saved);
		dermatologist = dermatologistService.create(dermatologist);
		
		try {
			emailService.sendTestMail(dermatologist);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(new DermatologistDTO(dermatologist), HttpStatus.CREATED); 
		
		
	}
	@Transactional(readOnly = false)
	@DeleteMapping(value = "/{id}/{i}")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'PHARMACY_ADMIN')")
	public ResponseEntity<DermatologistDTO> deleteDermatologist(@PathVariable("id") String email, @PathVariable("i") Long i) {
		Dermatologist dermatologist = dermatologistService.findOne(email);	
		if (dermatologist != null) {
			try {
				if(dermatologist.getMedicalExaminations().size()== 0) {	
					for(WorkHour w : dermatologist.getWorkHour()) {
						if(w.getPharmacy().getId().equals(i)) {
							workHourService.delete(w.getId());
							}
					}
					return new ResponseEntity<>(new DermatologistDTO(dermatologist),HttpStatus.OK);
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
	
	public boolean check(Set<WorkHour> workHours, WorkHour wh) {
		
    	for(WorkHour w : workHours) {
    		if((w.getDay().ordinal() == wh.getDay().ordinal() && w.getWorkHourFrom().compareTo(wh.getWorkHourFrom())==0
    				&& w.getWorkHourTo().compareTo(wh.getWorkHourTo()) ==0) 
    				|| ((w.getDay().ordinal() == wh.getDay().ordinal() && w.getWorkHourFrom().compareTo(wh.getWorkHourFrom())<0
    	    				&& w.getWorkHourTo().compareTo(wh.getWorkHourTo())>0 && wh.getWorkHourFrom().compareTo(w.getWorkHourTo())<0)) ||
    				((w.getDay().ordinal() == wh.getDay().ordinal() && w.getWorkHourFrom().compareTo(wh.getWorkHourFrom())>0
       	    	    				&& w.getWorkHourTo().compareTo(wh.getWorkHourTo())>0 && wh.getWorkHourTo().compareTo(w.getWorkHourFrom())>0))
    			|| ((w.getDay().ordinal() == wh.getDay().ordinal() && wh.getWorkHourFrom().compareTo(w.getWorkHourFrom())>0
    	    	    				&& wh.getWorkHourTo().compareTo(w.getWorkHourTo())>0 && wh.getWorkHourFrom().compareTo(w.getWorkHourTo())<0))) {
				return true;
    	}}
    	
    	return  false;
    }
	
	
	
	@PutMapping(value= "/updateDermatologist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'DERMATOLOGIST')")
	public ResponseEntity<DermatologistDTO> updateDermatologist(@RequestBody DermatologistDTO dermatologist,@PathVariable("id") String email) throws Exception {
		Dermatologist dermatologistUpdate = dermatologistService.findOne(email);
		Pharmacy p = pharmacyService.findOne(dermatologist.getPharmacies().get(0).getId());
		for(WorkHourDTO w: dermatologist.getWorkHours()){
			if(w.getWorkHourFrom() !=null && w.getWorkHourTo() !=null) {
			WorkHour v = new WorkHour();
			v.setId(w.getId());
			v.setDeleted(false);
			v.setWorkHourFrom(w.getWorkHourFrom());
			v.setWorkHourTo(w.getWorkHourTo());
			v.setPharmacy(p);
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
			if(check(dermatologistUpdate.getWorkHour(), v)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			WorkHour savedW = workHourService.create(v);
			dermatologistUpdate.getWorkHour().add(savedW);
			}
		}
		dermatologistUpdate.getPharmacy().add(p);
		
		dermatologistUpdate = dermatologistService.update(dermatologistUpdate);
		return new ResponseEntity<DermatologistDTO>(dermatologist, HttpStatus.OK);
	}

	@GetMapping(value = "/examinations")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	// pregledi za trenutno ulogovanog dermatologa
	public ResponseEntity<Collection<Appointment>> getUpcomingExaminationsForDermatologist() {
		Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Appointment> upcomingAppointments = dermatologistService.getUpcomingExaminationsForDermatologist(currentDermatologist.getEmail());
		//Collection<Appointment> upcomingAppointments = dermatologistService.getUpcomingExaminationsForDermatologist("aleksandarstevanovic@gmail.com");

		if (upcomingAppointments == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if (upcomingAppointments.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Appointment>>(upcomingAppointments, HttpStatus.OK);
	}

	@GetMapping(value = "/examinations/done/patient")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	// svi gotovi pregledi (sa pacijentima) za trenutno ulogovanog dermatologa
	public ResponseEntity<Collection<Appointment>> getAllDoneExaminationsWithPatientsForDermatologist() {
		Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Appointment> doneExaminations = dermatologistService.getDoneExaminationsWithPatientsForDermatologist(currentDermatologist.getEmail());

		if (doneExaminations == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if (doneExaminations.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Appointment>>(doneExaminations, HttpStatus.OK);
	}

	@GetMapping(value = "/examinations/existing")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	// svi slobodni termini pregleda za trenutno ulogovanog dermatologa
	public ResponseEntity<Collection<Appointment>> getAllExistingExaminationsForDermatologist() {
		Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Appointment> existingExaminations = dermatologistService.getAllExistingExaminationsForDermatologist(currentDermatologist.getEmail());

		if (existingExaminations == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if (existingExaminations.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Appointment>>(existingExaminations, HttpStatus.OK);
	}
	
	@GetMapping(value="/ratings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public double getRatings(@PathVariable("id") String email) {
	
		Dermatologist d = dermatologistService.getRatings(email);
		try {
			d.getRatings();
		}catch(NullPointerException e) {
			return 0;
		}
		
		
		double val = 0;
		
		for(Rating g : d.getRatings()) {
			val+=g.getValue();
		}
		val= val/d.getRatings().size();
		return val;
	}
	@PutMapping(value= "/changePassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	public ResponseEntity<DermatologistDTO> changePassword(@RequestBody DermatologistDTO dermatologist,@PathVariable("id") String password) throws Exception {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				dermatologist.getEmail(), password));
		Dermatologist dermatologistUpdate = dermatologistService.getOneWithAddress(dermatologist.getEmail());
		dermatologistUpdate.setPassword(passwordEncoder.encode(dermatologist.getPassword()));
		if(!dermatologistUpdate.isActive()) {
			dermatologistUpdate.setActive(true);
		}
		
		dermatologistUpdate = dermatologistService.update(dermatologistUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/appointments/schedule-new/{medical-report-id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	public ResponseEntity<String> createAndScheduleNewAppointment(@RequestBody AppointmentDTO appointmentDTO, @PathVariable("medical-report-id") Long medicalReportId) {
		Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocalDate date = appointmentDTO.getDate();
		LocalTime timeFrom = appointmentDTO.getTermFrom();
		LocalTime timeTo = appointmentDTO.getTermTo();
		String patientEmail = appointmentDTO.getPatientEmail();

		String message = dermatologistService.createAndScheduleNewAppointment(currentDermatologist.getEmail(), patientEmail, date, timeFrom, timeTo, medicalReportId);

		if (message != null) {
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

		try {
			emailService.appointmentScheduledMail(date,timeFrom, timeTo,currentDermatologist,patientEmail);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/appointments/calendar")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	public ResponseEntity<Collection<AppointmentCalendarDTO>> getAllAppointmentsBetweenDatesForCalendar(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
		Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<AppointmentCalendarDTO> appointments = dermatologistService.getAllAppointmentsBetweenDatesForCalendar(startDate, endDate, currentDermatologist.getEmail());

		if (appointments == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (appointments.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(appointments, HttpStatus.OK);
	}
}

