package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.PatientDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.PrescriptionMedicamentDTO;
import com.mrsisa.mrsisaprojekat.dto.SubscribedPharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.*;
import com.mrsisa.mrsisaprojekat.exceptions.ReservationQuantityException;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.ConfirmationTokenRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.*;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
	private PharmacyAdminService adminService;
	
	@Autowired
	private SystemAdminService sysAdminService;
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
    private ConfirmationTokenRepositoryDB confirmationTokenRepository;

	@Autowired
	private PrescriptionMedicamentService prescriptionMedicamentService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DermatologistService dermatologistService;

	@Autowired
	private MedicamentService medicamentService;


	@GetMapping(value="/reservedMedication/{id}")
	public ResponseEntity<Collection<PrescriptionMedicamentDTO>> getReservedMedication(@PathVariable("id") String id) {
		Patient patient = patientService.getOneWithReservedMeds(id);

		if(patient == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<PrescriptionMedicamentDTO> returns = new ArrayList<>();
		for(PrescriptionMedicament pm : patient.getReservedMedicaments()) {
			if(!pm.isDeleted() && !pm.isPurchased()) {
				PrescriptionMedicamentDTO medicament = new PrescriptionMedicamentDTO(pm);
				System.out.println(medicament.getId());
				returns.add(medicament);
			}

		}

		return new ResponseEntity<>(returns,HttpStatus.OK);


	}



	@GetMapping(value="/reservedAppointments/{id}")
	public ResponseEntity<Collection<AppointmentDTO>> getReservedAppointments(@PathVariable("id") String id) {
		Patient patient = patientService.getOneWithAppointments(id);

		if (patient == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<AppointmentDTO> returns = new ArrayList<>();
		for(Appointment a : patient.getAppointments()) {
			if(!a.isDone()) {
				AppointmentDTO d = new AppointmentDTO(a);
				returns.add(d);
			}

		}

		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) throws Exception{
		try {
			AdminPharmacy savedAdmin = adminService.findOne(patientDTO.getEmail());
			if(savedAdmin != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Patient patient = patientService.findOne(patientDTO.getEmail());
			if(patient != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			AdminSystem adminsystem = sysAdminService.findOne(patientDTO.getEmail());
			if(adminsystem != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Dermatologist dermatologist = dermatologistService.findOne(patientDTO.getEmail());
			if(dermatologist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Pharmacist pharmacist = pharmacistService.findOne(patientDTO.getEmail());
			if(pharmacist != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(NullPointerException e) {
			
		}
		
		
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
			p.setReservedMedicaments(null);
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
		System.out.println("REZERVACIJA: " + medicamentToReserve.getExpiryDate());
		try {
			patientService.checkMedicamentReservationQuantity(medicamentToReserve);
		} catch(ReservationQuantityException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getQuantity());
		}

		try {
			PrescriptionMedicament reservedMedicament = patientService.updateWithReservation(p, medicamentToReserve);
			emailService.ReservationConfirmationMail(p, reservedMedicament);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(medicament, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "cancelReservation/{id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
	public ResponseEntity<Long> cancelMedicamentReservation(@PathVariable("id") Long id) {
		PrescriptionMedicament medicament = prescriptionMedicamentService.findOne(id);
		System.out.println("LEEEEEEEEEEEEK:" + medicament.getId());
		if(medicament != null) {
			if(LocalDate.now().plusDays(1).isBefore(medicament.getExpiryDate())) {
				prescriptionMedicamentService.delete(id);

				return new ResponseEntity<>(id, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@PostMapping(value="/subscribe")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<SubscribedPharmacyDTO> subscribeToPharmacy(@RequestBody SubscribedPharmacyDTO pharmacyDTO) throws Exception{
		Patient patient = patientService.findOne(pharmacyDTO.getUser());
		Collection<Pharmacy> subscribedPharmacies = patientService.findAllSubscribed(pharmacyDTO.getUser());
		
		if(subscribedPharmacies == null) {
			subscribedPharmacies = new ArrayList<Pharmacy>();
		}
		
		subscribedPharmacies.add(pharmacyDTO.getPharmacy());
		
		patient.setSubscribedPharmacies(new HashSet<Pharmacy>(subscribedPharmacies));
		patient = patientService.update(patient);
		
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.CREATED);
	} 

	@PostMapping(value="/unsubscribe")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<SubscribedPharmacyDTO> unsubscrubeToPharmacy(@RequestBody SubscribedPharmacyDTO pharmacyDTO) throws Exception{
		Patient patient = patientService.findOne(pharmacyDTO.getUser());
		Collection<Pharmacy> subscribedPharmacies = patientService.findAllSubscribed(pharmacyDTO.getUser());
		if(subscribedPharmacies == null) {
			return new ResponseEntity<>(pharmacyDTO, HttpStatus.BAD_REQUEST);
		}
		
		for(Pharmacy p: subscribedPharmacies) {
			if(p.getId() == pharmacyDTO.getPharmacy().getId()) {
				subscribedPharmacies.remove(p);
				break;
			}
		}
		patient.setSubscribedPharmacies(new HashSet<Pharmacy>(subscribedPharmacies));
		patient = patientService.update(patient);
		
		
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/subscribedPharmacies/{id},{pharmacy}")
	public ResponseEntity<String> getSubscribedPharmacies(@PathVariable("id") String id, @PathVariable("pharmacy") Long pharmacy){
		
		Collection<Pharmacy> subscribedPharmacies = patientService.findAllSubscribed(id);
		if(subscribedPharmacies == null) {
			return new ResponseEntity<String>("Not_found", HttpStatus.OK);
		}
		for(Pharmacy p : subscribedPharmacies) {
			if(p.getId() == pharmacy) {
				return new ResponseEntity<String>("Found", HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("Not_found", HttpStatus.OK);
	}
//	@PostMapping(path = "/reserve_appointment", consumes = "application/json")
//	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
//	public ResponseEntity<AppointmentDTO> reserveExamination(@RequestBody AppointmentDTO appointment) throws Exception {
//
//		Appointment appointmentToReserve = appointmentService.findOne(appointment.getAppointmentId());
//		appointmentToReserve.setPatient(patientService.findOne(appointment.getPatientEmail()));
//
//		appointmentService.update(appointmentToReserve);
//		if(appointmentToReserve.getType() == Appointment.AppointmentType.EXAMINATION) {
//			Dermatologist d = dermatologistService.findOneExaminations(appointmentToReserve.getChosenEmployee().getEmail());
//
//			Set<Appointment> temp = d.getMedicalExaminations();
//			temp.add(appointmentToReserve);
//			d.setMedicalExaminations(temp);
//
//			dermatologistService.update(d);
//		} else {
//			Pharmacist p = pharmacistService.findOneCounselings(appointmentToReserve.getChosenEmployee().getEmail());
//			p.getCounselings().add(appointmentToReserve);
//			pharmacistService.update(p);
//		}
//
//		Patient p = patientService.getOneWithAppointments(appointment.getPatientEmail());
//
//		Long id = patientService.updateWithAppointment(p, appointmentToReserve);
//
//
//
//		try {
//			emailService.ReserveExaminationMail(p, id);
//		}
//		catch( Exception e) {
//			System.out.println(e.getMessage());
//		}
//
//		return new ResponseEntity<>(appointment, HttpStatus.CREATED);
//
//	}

	@PostMapping(value = "/add_allergy")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Object> addAllergy(@RequestBody AddAllergyDTO allergy) {

		try {
			patientService.addAllergy(allergy.getPatientEmail(), allergy.getMedicamentId());
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Allergy to this medicament is already set");
		}

		return ResponseEntity.ok().body(allergy);
	}

	@PostMapping(value = "/remove_allergy")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Object> removeAllergy(@RequestBody AddAllergyDTO allergy) {

		try {
			patientService.removeAllergy(allergy.getPatientEmail(), allergy.getMedicamentId());
		} catch (Exception e) {

			return new ResponseEntity<>(allergy, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(allergy, HttpStatus.OK);
	}

	@GetMapping(value = "/get_allergies/{email}")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Collection<MedicamentDTO>> getAllergies(@PathVariable("email") String email) {
		ArrayList<MedicamentDTO> retVal = new ArrayList<>();

		Patient p = patientService.getPatientAllergies(email);

		for(Medicament m : p.getAllergies()) {
			retVal.add(new MedicamentDTO(m));
		}

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete_examination/{id}")
	//@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Long> cancelExamination(@PathVariable("id") Long id) {
		Appointment a = appointmentService.findOne(id);
		LocalDateTime now = LocalDateTime.now().plusDays(1);

		if(a != null) {
			if(now.isBefore(a.getDate().atTime(a.getTermFrom()))) {
				appointmentService.cancelExamination(a);
				return new ResponseEntity<>(id, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@PostMapping(value = "/rating", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addRating(@RequestBody RatingDTO ratingDTO) {
		Rating rating = new Rating();
		rating.setValue(ratingDTO.getRating());
		rating.setPatient(patientService.findOne(ratingDTO.getPatientEmail()));

		switch (ratingDTO.getRateType()) {
			case MEDICAMENT:
				medicamentService.addRating(rating, ratingDTO.getRatedEntityId());
				break;
			case DERMATOLOGIST:
				dermatologistService.addRating(rating, ratingDTO.getRatedEmployeeEmail());
				break;
			case PHARMACIST:
				pharmacistService.addRating(rating, ratingDTO.getRatedEmployeeEmail());
				break;
			case PHARMACY:
				pharmacyService.addRating(rating, ratingDTO.getRatedEntityId());
				break;
		}
		return ResponseEntity.ok().body("Ocenili ste ovaj entitet");
	}

	@GetMapping(value = "/get_rating/{email}/{id}/{type}")
	public ResponseEntity<Integer> getRatingOfUser(@PathVariable("email") String email, @PathVariable("id") String entityId,
												   @PathVariable("type") String type) {
		Integer rating = -1;
		switch (type) {
			case "medicament":
				rating = medicamentService.getRatingOfUser(Long.parseLong(entityId), email);
				break;
			case "pharmacy":
				rating = pharmacyService.getRatingOfUser(Long.parseLong(entityId), email);
				break;
			case "dermatologist":
				rating = dermatologistService.getRatingOfUser(entityId, email);
				break;
			case "pharmacist":
				rating = pharmacistService.getRatingOfUser(entityId, email);
				break;
			default:
				return ResponseEntity.badRequest().body(rating);
		}

		return ResponseEntity.ok().body(rating);
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
