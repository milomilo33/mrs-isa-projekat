package com.mrsisa.mrsisaprojekat.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.MedicamentItemDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;

import com.mrsisa.mrsisaprojekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.WorkHour;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private DermatologistService dermatologistService;

	@Autowired
	private PharmacistService pharmacistService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PharmacyDTO>> getPharmacies(){
		
		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for(Pharmacy p : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(p));

		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyDTO> getPharmacy(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOne(id);

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new PharmacyDTO(pharmacy), HttpStatus.OK);
	}

	@GetMapping(value = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PharmacyDTO>> getPharmacyWhere(@PathVariable("query") String query) {

		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		pharmacies = pharmacies.stream().filter(p -> ((p.getName() + p.getDescription() +
			p.getAddress().getCountry() + p.getAddress().getCity() + p.getAddress().getStreet() +  p.getAddress().getNumber())
				.toLowerCase().contains(query))).collect(Collectors.toCollection(ArrayList::new));
		for (Pharmacy p : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(p));
		}
		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN')")
	public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) throws Exception {
		Address address = addressService.create(pharmacy.getAddress());
		pharmacy.setAddress(address);
		Pharmacy savedPharmacy = pharmacyService.create(pharmacy);
		return new ResponseEntity<>(savedPharmacy, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/medicamentItems/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<MedicamentItemDTO>> getPharmacyMedicamentItems(@PathVariable("id") Long id) {
		Collection<MedicamentItem> items = pharmacyService.getAllMedicaments(id);
		List<MedicamentItemDTO> returns = new ArrayList<>();
		for(MedicamentItem m: items) {
			if(!m.isDeleted()) {
				returns.add(new MedicamentItemDTO(m));
			}
		}
		if (items == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	
	@PutMapping(value= "/updateMedicaments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'PHARMACIST')")
	public ResponseEntity<PharmacyDTO> updatePharmacy(@RequestBody Pharmacy pharmacy,@PathVariable("id") Long id) throws Exception {
		Pharmacy pharmacyUpdate = pharmacyService.findOneWithMedicaments(id);
		for(MedicamentItem m : pharmacy.getMedicaments()) {
			pharmacyUpdate.getMedicaments().add(m);
			
		}
		if (pharmacyUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		pharmacyUpdate = pharmacyService.update(pharmacyUpdate);
		return new ResponseEntity<PharmacyDTO>(new PharmacyDTO(pharmacyUpdate), HttpStatus.OK);
	}
	
	@GetMapping(value = "/dermatologists/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN', 'DERMATOLOGIST')")
	public ResponseEntity<Collection<DermatologistDTO>> getPharmacyDermatologists(@PathVariable("id") String id) {
		Pharmacy pharmacy = pharmacyService.findOneWithDermatologists(Long.parseLong(id));
		List<DermatologistDTO> returns = new ArrayList<>();
		for(Dermatologist m: pharmacy.getDermatologists()) {
			m.setMedicalExaminations(new HashSet<>(dermatologistService.getAvailableAppointments(m)));
			if(!m.isDeleted()) {
				ArrayList<WorkHourDTO> hours = new ArrayList<WorkHourDTO>();
				for(WorkHour h : m.getWorkHour()) {
					if(h.getPharmacy().getId() == pharmacy.getId()) {
						WorkHourDTO wd = new WorkHourDTO(h);
						hours.add(wd);
					}
				}
				DermatologistDTO d = new DermatologistDTO(m);
				d.setWorkHours(hours);
				ArrayList<AppointmentDTO> lista = new ArrayList<AppointmentDTO>();
				for(Appointment a : pharmacyService.findAllAppointmentsDeramtologist(d.getEmail(), pharmacy.getId())) {
					lista.add(new AppointmentDTO(a));
				}
				d.setAllAppointments(lista);
				returns.add(d);
			}
		}
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	
	@GetMapping(value = "/pharmacists/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN', 'PHARMACIST')")
	public ResponseEntity<Collection<PharmacistDTO>> getPharmacyPharmacists(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithPharmacists(id);
		List<PharmacistDTO> returns = new ArrayList<>();
		for(Pharmacist p: pharmacy.getPharmacists()) {
			if(!p.isDeleted()) {
				p.setCounselings(new HashSet<>(pharmacistService.getAvailableAppointments(p)));
				ArrayList<WorkHourDTO> hours = new ArrayList<WorkHourDTO>();
				for(WorkHour h : p.getWorkHour()) {
					WorkHourDTO wd = new WorkHourDTO(h);
					hours.add(wd);
				}
				PharmacistDTO d = new PharmacistDTO(p);
				d.setWorkHours(hours);
				returns.add(d);
			}
		}
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	
	@GetMapping(value = "/appointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN', 'PHARMACIST')")
	public ResponseEntity<Collection<AppointmentDTO>> getPharmacyAppointments(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		List<AppointmentDTO> returns = new ArrayList<>();
		for(Appointment a : pharmacy.getAppointments()) {
			if(!a.isDone() && a.getPatient() == null) {
				AppointmentDTO d = new AppointmentDTO(a);
				returns.add(d);
				}
				
		}
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}

	
	
	
	@GetMapping(value = "filter/distance={minDistance},{maxDistance}&rating={rating}")
	public ResponseEntity<Collection<PharmacyDTO>> filterPharmacies(@PathVariable("minDistance") int minDistance,
																	@PathVariable("maxDistance") int maxDistance,
																	@PathVariable("rating") int rating) {
		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();

		if(rating != -1) {
			for (Pharmacy p : pharmacies) {

				if (pharmacyService.getRating(p.getId()) == rating)
					pharmaciesDTO.add(new PharmacyDTO(p));
			}
		}
		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}


	
}
