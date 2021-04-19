package com.mrsisa.mrsisaprojekat.controller;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.dto.PatientDTO;
import com.mrsisa.mrsisaprojekat.dto.PrescriptionMedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import com.mrsisa.mrsisaprojekat.service.PrescriptionMedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private PrescriptionMedicamentService prescriptionMedicamentService;
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) throws Exception{
		
		Address address = new Address();
		address.setCountry(patientDTO.getAddress().getCountry());
		address.setCity(patientDTO.getAddress().getCity());
		address.setStreet(patientDTO.getAddress().getStreet());
		address.setNumber(patientDTO.getAddress().getNumber());
		
		Address saved = addressService.create(address);
		
		Patient patient = new Patient();
		patient.setEmail(patientDTO.getEmail());
		patient.setPassword(patientDTO.getPassword());
		patient.setName(patientDTO.getName());
		patient.setLastName(patientDTO.getLastName());
		patient.setPhoneNumber(patientDTO.getPhoneNumber());
		patient.setAddress(saved);
		patient.setCategory(Category.REGULAR);
		patient.setLoyaltyPoints(0);
		patient.setPenaltyPoints(0);
		patient = patientService.create(patient);
		
		try {
			emailService.sendTestMail(patient);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.CREATED); 
	}
	
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<Patient>> searchPatients(@RequestParam String name, @RequestParam String lastName) {
		Collection<Patient> foundPatients = patientService.findByNameAndLastName(name, lastName);
		for (Patient p : foundPatients) {
			p.setAllergies(null);
			p.setAppointments(null);
			p.setComplaints(null);
			p.setePrescriptions(null);
			p.setSubscribedPharmacies(null);
		}
		
		if (foundPatients == null || foundPatients.isEmpty())
			return new ResponseEntity<Collection<Patient>>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Collection<Patient>>(foundPatients, HttpStatus.OK);
	}

	@PostMapping(path = "/reserve", consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<PrescriptionMedicamentDTO> reserveMedicament(@RequestBody PrescriptionMedicamentDTO medicament) throws Exception {

		PrescriptionMedicament medicamentToReserve = new PrescriptionMedicament();

		medicamentToReserve.setDeleted(false);
		medicamentToReserve.setPurchased(false);
		medicamentToReserve.setExpiryDate(medicament.getExpiryDate());
		medicamentToReserve.setQuantity(medicament.getQuantity());
		medicamentToReserve.setMedicament(medicament.getMedicament());
		Patient p = patientService.findOne(medicament.getPatientEmail());
		p.getReservedMedicaments().add(medicamentToReserve);
		patientService.update(p);



		try {
			emailService.ReservationConfirmationMail(p);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(medicament, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Long> cancelMedicamentReservation(@PathVariable("id") Long id) {
		PrescriptionMedicament medicament = prescriptionMedicamentService.findOne(id);

		if(medicament != null) {
			prescriptionMedicamentService.delete(id);
			return new ResponseEntity<>(id, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//	@GetMapping(value = "/{id}/appointments")
//	public ResponseEntity<Collection<Appointment>> getUpcomingAppointmentsForUser(@PathVariable("id") String id, @RequestParam String type) {
//		// dodati proveru tipa korisnika na osnovu tokena i dozvoliti samo ako je farmaceut ili dermatolog (ili admin?)
//
//		Appointment.AppointmentType apType = null;
//		if (type.equals("examination")) {
//			apType = Appointment.AppointmentType.EXAMINATION;
//		}
//		else if (type.equals("counseling")) {
//			apType = Appointment.AppointmentType.COUNSELING;
//		}
//		else {
//			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//		}
//
//		Collection<Appointment> upcomingAppointments = patientService.getUpcomingAppointmentsForUser(id, apType);
//
//		return new ResponseEntity<Collection<Appointment>>(upcomingAppointments, HttpStatus.OK);
//	}
}
