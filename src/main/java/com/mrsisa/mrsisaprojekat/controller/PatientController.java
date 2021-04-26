package com.mrsisa.mrsisaprojekat.controller;

import java.net.URI;
import java.util.Collection;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.PatientDTO;
import com.mrsisa.mrsisaprojekat.dto.PrescriptionMedicamentDTO;
import com.mrsisa.mrsisaprojekat.exceptions.ReservationQuantityException;
import com.mrsisa.mrsisaprojekat.exceptions.ResourceConflictException;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.ConfirmationTokenRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.AddressService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import com.mrsisa.mrsisaprojekat.service.PrescriptionMedicamentService;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/patients")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    private ConfirmationTokenRepositoryDB confirmationTokenRepository;

	@Autowired
	private PrescriptionMedicamentService prescriptionMedicamentService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DermatologistService dermatologistService;

	@Autowired
	private PharmacistService pharmacistService;
	
	
	
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
		patient.setActive(false);
		patient.setCategory(Category.REGULAR);
		patient.setLoyaltyPoints(0);
		patient.setPenaltyPoints(0);
		patient = patientService.create(patient);
		
		ConfirmationToken token = new ConfirmationToken(patient);

        confirmationTokenRepository.save(token);
		try {
			emailService.activationTokenMail(token, patient.getEmail());
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.CREATED); 
	}
	
	@GetMapping(value="/confirm-account")
    public ResponseEntity<Void> confirmUserAccount(@RequestParam("token")String confirmationToken) throws Exception
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        
        if(token != null)
        {
        	Patient user = patientService.findOne(token.getUserEntity().getEmail());
            user.setActive(true);
            patientService.update(user);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8081/SuccessActivation")).build();
        }
        else
        {
        	return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8081/SuccessActivation")).build();
        }
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PatientDTO> getPatientDetalis(@RequestParam String email) {
		System.out.println(email);
		Patient p = patientService.getPatientDetails(email);
		PatientDTO patient = new PatientDTO(p);
		return new ResponseEntity<>(patient, HttpStatus.OK);
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
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
	public ResponseEntity<Object> reserveMedicament(@RequestBody PrescriptionMedicamentDTO medicament) throws Exception {

		PrescriptionMedicament medicamentToReserve = new PrescriptionMedicament();
		medicamentToReserve.setDeleted(false);
		medicamentToReserve.setPurchased(false);
		medicamentToReserve.setExpiryDate(medicament.getExpiryDate());
		medicamentToReserve.setQuantity(medicament.getQuantity());
		medicamentToReserve.setMedicament(medicament.getMedicament());
		Patient p = patientService.getOneWithReservedMeds(medicament.getPatientEmail());

		try {
			patientService.checkMedicamentReservationQuantity(medicamentToReserve);
		} catch(ReservationQuantityException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getQuantity());
		}

		try {
			Long id = patientService.updateWithReservation(p, medicamentToReserve);
			emailService.ReservationConfirmationMail(p, id);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(medicament, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
	public ResponseEntity<Long> cancelMedicamentReservation(@PathVariable("id") Long id) {
		PrescriptionMedicament medicament = prescriptionMedicamentService.findOne(id);

		if(medicament != null) {
			prescriptionMedicamentService.delete(id);
			return new ResponseEntity<>(id, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/reserve_appointment", consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
	public ResponseEntity<AppointmentDTO> reserveExamination(@RequestBody AppointmentDTO appointment) throws Exception {

		Appointment appointmentToReserve = appointmentService.findOne(appointment.getAppointmentId());
		appointmentToReserve.setPatient(patientService.findOne(appointment.getPatientEmail()));

		appointmentService.update(appointmentToReserve);
		if(appointmentToReserve.getType() == Appointment.AppointmentType.EXAMINATION) {
			Dermatologist d = dermatologistService.findOneExaminations(appointmentToReserve.getChosenEmployee().getEmail());

			Set<Appointment> temp = d.getMedicalExaminations();
			temp.add(appointmentToReserve);
			d.setMedicalExaminations(temp);

			dermatologistService.update(d);
		} else {
			Pharmacist p = pharmacistService.findOneCounselings(appointmentToReserve.getChosenEmployee().getEmail());
			p.getCounselings().add(appointmentToReserve);
			pharmacistService.update(p);
		}

		Patient p = patientService.getOneWithAppointments(appointment.getPatientEmail());

		Long id = patientService.updateWithAppointment(p, appointmentToReserve);



		try {
			emailService.ReserveExaminationMail(p, id);
		}
		catch( Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(appointment, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "/delete_examination/{id}")
	//@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Long> cancelExamination(@PathVariable("id") Long id) {
		Appointment a = appointmentService.findOne(id);

		if(a != null) {
			appointmentService.delete(a);
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
