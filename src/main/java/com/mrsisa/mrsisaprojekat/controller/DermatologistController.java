package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.WorkHour;
import com.mrsisa.mrsisaprojekat.model.WorkHour.Day;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;
import com.mrsisa.mrsisaprojekat.service.WorkHourService;

@RestController
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
	

	@GetMapping(value="/all")
	public ResponseEntity<List<DermatologistDTO>> getDermatologist(){
		
		List<Dermatologist> dermatologist = dermatologistService.findAll();
		
		List<DermatologistDTO> dermatologistDTO = new ArrayList<>();
		for(Dermatologist d : dermatologist) {
			
			DermatologistDTO pd = new DermatologistDTO(d);
			dermatologistDTO.add(pd);
			
		}
		
		return new ResponseEntity<>(dermatologistDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistDTO> getDermatologist(@PathVariable("id") String email) {
		Dermatologist dermatologist = dermatologistService.findOne(email);

		if (dermatologist == null) {
			return new ResponseEntity<DermatologistDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<DermatologistDTO>(new DermatologistDTO(dermatologist), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<DermatologistDTO> saveDermatologist(@RequestBody DermatologistDTO dermatologistDTO) throws Exception{
		
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
	@PutMapping(value= "/updateDermatologist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
			WorkHour savedW = workHourService.create(v);
			dermatologistUpdate.getWorkHour().add(savedW);
			}
		}
		dermatologistUpdate.getPharmacy().add(p);
		
		dermatologistUpdate = dermatologistService.update(dermatologistUpdate);
		return new ResponseEntity<DermatologistDTO>(dermatologist, HttpStatus.OK);
	}
}

