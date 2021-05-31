package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.*;
import com.mrsisa.mrsisaprojekat.exceptions.ReservationQuantityException;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.ConfirmationTokenRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.*;
import com.mrsisa.mrsisaprojekat.util.QRCodeReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//import javafx.application.Application;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/patients")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ePrescriptionService ePrescriptionService;

	@Autowired
	private PricelistItemMedicamentService pricelistService;
	
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
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MedicalReportService medicalReportService;

	@Autowired
	private MedicamentService medicamentService;

	@Autowired
	private CategoryThresholdsService categoryService;
	
	@GetMapping(value="/reservedMedication/{id}")
	public ResponseEntity<Collection<PrescriptionMedicamentDTO>> getReservedMedication(@PathVariable("id") String id) {
		Patient patient = patientService.getOneWithReservedMeds(id);

		if(patient == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<PrescriptionMedicamentDTO> returns = new ArrayList<>();
		for(PrescriptionMedicament pm : patient.getReservedMedicaments()) {
			if(!pm.isDeleted() && !pm.isPurchased()) {
				PrescriptionMedicamentDTO medicament = new PrescriptionMedicamentDTO(pm);
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

	@GetMapping(value = "/allSubscribed/{email}")
	public ResponseEntity<Collection<PharmacyDTO>> getAllSubscribedPharmacies(@PathVariable("email") String email) {
		Collection<Pharmacy> subscribedPharmacies = patientService.findAllSubscribed(email);
		Collection<PharmacyDTO> subPharmaciesDTO = new ArrayList<>();

		if(subscribedPharmacies == null) {
			return ResponseEntity.badRequest().body(null);
		}

		for(Pharmacy p : subscribedPharmacies)
		{
			subPharmaciesDTO.add(new PharmacyDTO(p));
		}

		return ResponseEntity.ok().body(subPharmaciesDTO);
	}

	@PostMapping(consumes = "application/json")
	//@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
	public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) throws Exception{
		try {
			AdminPharmacy savedAdmin = adminService.findOne(patientDTO.getEmail());
			if(savedAdmin != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			Patient patient = patientService.findOne(patientDTO.getEmail());
			if(patient != null) {
				Address a = addressService.findOne(patientDTO.getAddress().getId());

				a.setCountry(patientDTO.getAddress().getCountry());
				a.setCity(patientDTO.getAddress().getCity());
				a.setStreet(patientDTO.getAddress().getStreet());
				a.setNumber(patientDTO.getAddress().getNumber());
				a = addressService.update(a);
				System.out.println(a.getId());
				patient.setEmail(patientDTO.getEmail());
				patient.setName(patientDTO.getName());
				patient.setLastName(patientDTO.getLastName());
				patient.setPhoneNumber(patientDTO.getPhoneNumber());

				patient.setActive(false);
				patient.setCategory("REGULAR");
				patient.setLoyaltyPoints(0);
				patient.setPenaltyPoints(0);
				patient = patientService.update(patient);


				return ResponseEntity.ok().body(null);
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
		patient.setCategory("REGULAR");
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

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PatientDTO> getPatientDetalis(@PathVariable("email") String email) {
		
		Patient p = patientService.getOneWithAddress(email);

		PatientDTO patient = new PatientDTO(p.getEmail(), null, p.getName(), p.getLastName(), p.getPhoneNumber(), new AddressDTO(p.getAddress()));



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

		Patient p = patientService.getOneWithReservedMeds(medicament.getPatientEmail());
		if(p.getPenaltyPoints() == 3) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
		}
		PrescriptionMedicament medicamentToReserve = new PrescriptionMedicament();
		medicamentToReserve.setDeleted(false);
		medicamentToReserve.setPurchased(false);
		medicamentToReserve.setExpiryDate(medicament.getExpiryDate());
		medicamentToReserve.setQuantity(medicament.getQuantity());
		medicamentToReserve.setMedicament(medicament.getMedicament());
		//Patient p = patientService.getOneWithReservedMeds(medicament.getPatientEmail());
		try {
			patientService.checkMedicamentReservationQuantity(medicamentToReserve, medicament.getPharmacyId());
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

	@PostMapping(path = "/prescribe/{id}", consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Object> prescribeMedicament(@RequestBody PrescriptionMedicamentDTO medicament, @PathVariable("id") Long medicalReportId) throws Exception {
		if (!medicalReportService.reportBelongsToPatient(medicalReportId, medicament.getPatientEmail())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		PrescriptionMedicament medicamentToReserve = new PrescriptionMedicament();
		medicamentToReserve.setDeleted(false);
		medicamentToReserve.setPurchased(false);
		medicamentToReserve.setExpiryDate(medicament.getExpiryDate());
		medicamentToReserve.setQuantity(medicament.getQuantity());
		medicamentToReserve.setMedicament(medicament.getMedicament());
		//Patient p = patientService.getOneWithReservedMedsAndePrescriptions(medicament.getPatientEmail());
		try {
			boolean successful = patientService.checkMedicamentReservationQuantityForPrescription(medicamentToReserve, medicament.getPharmacyId(), employee);
			if (!successful) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch(ReservationQuantityException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getQuantity());
			// not found => zamenski lekovi
		}

		try {
			PrescriptionMedicament reservedMedicament = patientService.updateWithPrescription(medicament.getPatientEmail(), medicamentToReserve, medicalReportId);
			if (reservedMedicament == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			// mejl poslati prilikom zavrsetka pregleda za ePrescription
			//emailService.ReservationConfirmationMail(p, reservedMedicament);
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

	@PostMapping(path = "/reserve_appointment", consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
	public ResponseEntity<AppointmentDTO> reserveExamination(@RequestBody AppointmentDTO appointment) throws Exception {
		Patient patient = patientService.findOne(appointment.getPatientEmail());
		if(patient.getPenaltyPoints() == 3) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		
		Appointment appointmentToReserve = appointmentService.findOne(appointment.getAppointmentId());
		appointmentToReserve.setPatient(patientService.findOne(appointment.getPatientEmail()));

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
		Patient p = patientService.getOneWithAppointments(appointment.getPatientEmail());
		boolean isReserved = patientService.checkIfTermFilled(p, appointmentToReserve);

		if(!isReserved) {
			appointmentService.update(appointmentToReserve);
			Long id = patientService.updateWithAppointment(p, appointmentToReserve);
			try {
				emailService.ReserveExaminationMail(p, id);
			}
			catch( Exception e) {
				System.out.println(e.getMessage());
			}
			return new ResponseEntity<>(appointment, HttpStatus.CREATED);
		} else {
			return ResponseEntity.badRequest().body(null);
		}




	}

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

		if (p != null) {
			for (Medicament m : p.getAllergies()) {
				retVal.add(new MedicamentDTO(m));
			}
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

	@GetMapping(value = "/eprescription/{email}")
	public ResponseEntity<Collection<ePrescriptionPreviewDTO>> ePrescriptionsOfPatient(@PathVariable("email") String email) {
		Patient patient = patientService.getOneWithePrescriptions(email);
		if(patient == null) return ResponseEntity.badRequest().body(null);
		System.out.println(patient.getEmail());
		Collection<ePrescriptionPreviewDTO> ePrescriptionPreviewDTOS = new ArrayList<>();
		for(ePrescription ep : patient.getePrescriptions()) {
			ePrescriptionPreviewDTOS.add(new ePrescriptionPreviewDTO(ep));
		}


		return ResponseEntity.ok().body(ePrescriptionPreviewDTOS);
	}

	@GetMapping(value = "/appointment_history/{email}")
	public ResponseEntity<Collection<AppointmentDTO>> viewPastAppointments(@PathVariable("email") String email) {
		Patient patient = patientService.getOneWithAppointments(email);

		if (patient == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<AppointmentDTO> returns = new ArrayList<>();
		for(Appointment a : patient.getAppointments()) {
			if(a.isDone()) {
				returns.add(new AppointmentDTO(a));
			}
		}

		return new ResponseEntity<>(returns, HttpStatus.OK);
	}

	@GetMapping(value = "/medical_report/{id}")
	public ResponseEntity<AppointmentDetailsDTO> getMedicalReport(@PathVariable("id") Long id) {
		AppointmentDetailsDTO appointmentDetails = appointmentService.getAppointmentDetails(id);

		if(appointmentDetails == null) return ResponseEntity.badRequest().body(null);

		return ResponseEntity.ok().body(appointmentDetails);
	}

	@GetMapping(value="/uploadQRCode/{qrPath},{patient}")
	public ResponseEntity<QRCodePharmacyDTO> uploadQRCode(@PathVariable("qrPath") String qrPath, @PathVariable("patient") String patient ) throws IOException {
		//File file = new File("C:"+ File.separator+ "Users" + File.separator+ "filip" + File.separator + "Downloads" + File.separator + "qr4.png");
		Patient p = patientService.getOneWithAddress(patient);
		if(p.getPenaltyPoints() == 3) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		String decodedText = QRCodeReader.decodeQRCode("C:"+ File.separator+ "Users" + File.separator+ "filip"+ File.separator + "Downloads" + File.separator + ""+qrPath);
        if(decodedText == null) {
            System.out.println("No QR Code found in the image");
        } else {
            System.out.println("Decoded text = " + decodedText);
        }
        
        QRCodeDTO qrCode = getMedicamentsFromQRCode(decodedText);
        Collection<QRItemDTO> items = qrCode.getPrescriptionMedicaments();
        
        if(items == null) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Collection<PharmacyDTO> pharmacies = getAllPharmaciesWithMedicaments(items, p);
        if(pharmacies == null) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Set<PharmacyDTO> dtos = new HashSet<PharmacyDTO>();
        for(PharmacyDTO pharmacy : pharmacies) {
        	dtos.add(pharmacy);	
        }
        
        QRCodePharmacyDTO dto = new QRCodePharmacyDTO(qrCode, dtos);
        return new ResponseEntity<>(dto, HttpStatus.OK);
		
	}
	private QRCodeDTO getMedicamentsFromQRCode(String decodedText) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
        QRCodeDTO qrDTO = objectMapper.readValue(decodedText, QRCodeDTO.class); 
        return qrDTO;
        
	}

	private Collection<PharmacyDTO> getAllPharmaciesWithMedicaments(Collection<QRItemDTO> items, Patient patient){
		Collection<Pharmacy> pharmacies = pharmacyService.getAllWithMedicaments();
		if(pharmacies == null) {
			return null;
		}
		
		List<CategoryThresholds> cts = categoryService.findAll();
		int discount = 0;
		for(CategoryThresholds c: cts) {
			if(patient.getCategory() == c.getCategory()) {
				discount = c.getDiscount();
			}	
		}
		
		int itemSize = items.size();
		double value = 0;
		int nmb = 0;
		Set<PharmacyDTO> dtos = new HashSet<PharmacyDTO>();
		for(Pharmacy p: pharmacies) {
			for(MedicamentItem medicament : p.getMedicamentItems()) {
				for(QRItemDTO item : items) {
					if(medicament.getMedicament().getId() == item.getMedicamentId() && medicament.getQuantity() >= item.getQuantity()) {
						nmb++;
						item.setName(medicament.getMedicament().getName());
						item.setPoints(medicament.getMedicament().getPoints());
						PricelistItemMedicament plim = pricelistService.findOneInPharmacy(medicament.getMedicament().getId(), p.getId());
						if(plim != null) {
							for(Price price: plim.getPrice()) {
								if(!price.isDeleted()) {
									value+=(price.getValue()*item.getQuantity());
									break;
								}
							}
						}
					}
				}
			}
			if(nmb == itemSize) {
				AddressDTO address = new AddressDTO(p.getAddress());
				int rating = pharmacyService.getRating(p.getId());
				value = (value - ((value*discount)/100));
				PharmacyDTO pharmacyDTO = new PharmacyDTO(p.getId(), p.getName(), p.getDescription(), value, address, rating);
				dtos.add(pharmacyDTO);
				value = 0;
				nmb = 0;
			}
			else {
				value = 0;
				nmb = 0;
			}
		}
		
		return dtos;
	}
	
	@PostMapping(value="/addEPrescription")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<QRCodePharmacyDTO> createePrescription(@RequestBody QRCodePharmacyDTO dto) throws Exception{
		
		Patient p = patientService.getOneWithAddress(dto.getQrCode().getPatient());
		if(p == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pharmacy pharmacy = pharmacyService.findOneWithMedicaments(dto.getPharmacy().getId());
		if(pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for(MedicamentItem m : pharmacy.getMedicamentItems()) {
			for(QRItemDTO item : dto.getQrCode().getPrescriptionMedicaments()) {
				if(m.getMedicament().getId() == item.getMedicamentId()) {
					m.setQuantity(m.getQuantity()-item.getQuantity());
				}
			}
		}
		pharmacy = pharmacyService.update(pharmacy);
		
		int points = 0;
		Set<PrescriptionMedicament> prescribedMeds = new HashSet<PrescriptionMedicament>();
		for(QRItemDTO item : dto.getQrCode().getPrescriptionMedicaments()) {
			PrescriptionMedicament pm = new PrescriptionMedicament();
			pm.setPurchased(true);
			pm.setDeleted(false);
			pm.setExpiryDate(LocalDate.now());
			pm.setQuantity(item.getQuantity());
			pm.setMedicament(medicamentService.findOne(item.getMedicamentId()));
			prescribedMeds.add(pm);
			points += (item.getPoints()*item.getQuantity());
		}
		
		p.setLoyaltyPoints(p.getLoyaltyPoints()+points);
		
		CategoryThresholds ct = new CategoryThresholds();
		List<CategoryThresholds> cts = categoryService.findAll();
		int index = 0;
		for(CategoryThresholds c: cts) {
			if(p.getLoyaltyPoints() > c.getThreshold()) {
				index = index + 1;
			}	
		}
		if(index == cts.size()) {
			index = index - 1;
		}
		ct = cts.get(index);
		p.setCategory(ct.getCategory());
		int discount = ct.getDiscount();
		
		double totalPrice = dto.getPharmacy().getCost() - ((dto.getPharmacy().getCost() * discount)/100);
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(dto.getQrCode().getDate(), formatter);
		
		ePrescription prescription = new ePrescription();
		prescription.setDate(localDate);
		prescription.setDeleted(false);
		prescription.setDone(true);
		prescription.setPatient(p);
		prescription.setPrescriptionMedicaments(prescribedMeds);
		prescription.setPrice(totalPrice);
		prescription.setTakenDate(LocalDate.now());
		prescription.setPharmacy(pharmacy);
		
		prescription = ePrescriptionService.create(prescription);
		
		if(prescription == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		dto.setId(prescription.getId());
		emailService.sendMedicineTakenConfirmationMail(prescription);
		return new ResponseEntity<QRCodePharmacyDTO>(dto, HttpStatus.OK);
		
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
	
	
	@PutMapping(value= "/changePassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<PatientDTO> changePassword(@RequestBody PatientDTO patient,@PathVariable("id") String password) throws Exception {
		@SuppressWarnings("unused")
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				patient.getEmail(), password));
		Patient patientUpdate = patientService.findOne(patient.getEmail());
		
	
		patientUpdate.setPassword(passwordEncoder.encode(patient.getPassword()));
		if(!patientUpdate.isActive()) {
			patientUpdate.setActive(true);
		}
		
		patientUpdate = patientService.update(patientUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@SuppressWarnings("unused")
	@GetMapping(value = "/loyalty/{email}")
	public ResponseEntity<PatientLoyaltyDTO> getPatientLoyalty(@PathVariable("email") String email) {
		Patient p = patientService.findOne(email);

		PatientLoyaltyDTO loyaltyDTO = new PatientLoyaltyDTO(p.getLoyaltyPoints(), p.getCategory());

		if(loyaltyDTO == null)
		{
			return ResponseEntity.badRequest().body(null);
		}

		return ResponseEntity.ok().body(loyaltyDTO);
	}
	
	@Scheduled(cron = "0 * * 1 * *")
	public void checkPenaults() throws Exception {
		ArrayList<Patient> allPatients = (ArrayList<Patient>) patientService.findAll();
		for(Patient p : allPatients) {
			if(p.getPenaltyPoints() == 3) {
				p.setPenaltyPoints(0);
				patientService.update(p);
			}
		}
		
		
		
	}
}
