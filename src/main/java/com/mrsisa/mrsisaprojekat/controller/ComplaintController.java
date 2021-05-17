package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AddressDTO;
import com.mrsisa.mrsisaprojekat.dto.ComplaintDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Appointment.AppointmentType;
import com.mrsisa.mrsisaprojekat.model.Complaint;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Employee;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.ePrescription;
import com.mrsisa.mrsisaprojekat.service.ComplaintService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import com.mrsisa.mrsisaprojekat.service.PharmacistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;
import com.mrsisa.mrsisaprojekat.service.SystemAdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/complaints")
public class ComplaintController {
	
	@Autowired 
	private EmailService emailService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private SystemAdminService adminService;
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PatientService patientService;
	
	@GetMapping(value="/allPharmacies/{email}")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Set<PharmacyDTO>> getAllPossiblePharmacies(@PathVariable("email") String email){

		Patient p = patientService.findAllWithPurchasedMedicamentsAndAppointments(email);
		Set<PharmacyDTO> pharmacies = new HashSet<PharmacyDTO>();
		Set<Long> ids = new HashSet<Long>();
		for(ePrescription e : p.getePrescriptions()) {
			Pharmacy pharmacy = e.getPharmacy();
			AddressDTO address = new AddressDTO(pharmacy.getAddress());
			PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy.getId(),  pharmacy.getName(),pharmacy.getDescription(), address);
			if(!ids.contains(pharmacy.getId())) {
				pharmacies.add(pharmacyDTO);
				ids.add(pharmacy.getId());
			}
			
		
		}
		for(Appointment a : p.getAppointments()) {
			if(a.getType() == AppointmentType.COUNSELING) {
				Pharmacy pharmacy = (pharmacistService.findOne(a.getChosenEmployee().getEmail())).getPharmacy();
				AddressDTO address = new AddressDTO(pharmacy.getAddress());
				PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy.getId(),  pharmacy.getName(), pharmacy.getDescription(), address);
				if(!ids.contains(pharmacy.getId())) {
					pharmacies.add(pharmacyDTO);
					ids.add(pharmacy.getId());
				}
			}
		}
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping(value="/allDermatologists/{email}")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Set<DermatologistDTO>> getAllPossibleEmployees(@PathVariable("email") String email){
		Patient p = patientService.findAllWithPurchasedMedicamentsAndAppointments(email);
		Set<DermatologistDTO> employees = new HashSet<DermatologistDTO>();
		try {
			for(Appointment a: p.getAppointments()) {
				
				if(a.isDone() && a.getType() == AppointmentType.EXAMINATION) {
					Dermatologist dermatologist = dermatologistService.getOneWithAddress(email);
					DermatologistDTO dto = new DermatologistDTO();
					dto.setEmail(email);
					dto.setName(dermatologist.getName());
					dto.setLastName(dermatologist.getLastName());
					employees.add(dto);
				}
			}
		}
		catch(NullPointerException e) {
			
		}
		
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@GetMapping(value="/allPharmacist/{email}")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Set<PharmacistDTO>> getAllPossiblePharmacist(@PathVariable("email") String email){
		Patient p = patientService.findAllWithPurchasedMedicamentsAndAppointments(email);
		Set<PharmacistDTO> employees = new HashSet<PharmacistDTO>();
		try {
			for(Appointment a: p.getAppointments()) {
				if(a.isDone() && a.getType() == AppointmentType.COUNSELING) {
					Pharmacist dermatologist = pharmacistService.findOne(email);
					PharmacistDTO dto = new PharmacistDTO(dermatologist);
					employees.add(dto);
				}
			}
			
		}
		catch(NullPointerException e) {
			
		}
		return new ResponseEntity<>(employees, HttpStatus.OK);
		
	}
	
	@PostMapping(value="/save/{email}")
	@PreAuthorize("hasAnyRole('PATIENT', 'SYSTEM_ADMIN')")
	public ResponseEntity<ComplaintDTO> saveComplaint(@RequestBody ComplaintDTO complaint, @PathVariable("email") String email) throws Exception{
		Employee employee = null;
		
		if(complaint.getEmployee() != null) {
			employee = pharmacistService.findOne(complaint.getEmployee());
			if(employee == null) {
				employee = dermatologistService.findOne(complaint.getEmployee());
			}
			
			if(employee == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		Pharmacy pharmacy = null;
		
		if(complaint.getPharmacy() != null) {
			pharmacy = pharmacyService.findOne(complaint.getPharmacy());
			if(pharmacy == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		Patient p = patientService.findAllWithPurchasedMedicamentsAndAppointments(email);
		
		if(employee != null) {
			boolean employeeFound = false;
			
			try {
				employeeFound = this.isEmployeeAvaiable(employee.getEmail(), p.getAppointments());
				
			}
			catch(NullPointerException e) {
				employeeFound = false;
			}
			
			if(!employeeFound) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		if(pharmacy != null) {
			boolean medicamentFound = false;
			boolean appointmentFound = false;
			
			try {
				medicamentFound = this.isMedicamentAvaiable(p.getePrescriptions(), pharmacy.getId());
				
			}
			catch(NullPointerException e) {
				medicamentFound = false;
			}
			
			try {
				appointmentFound = this.isPharmacyAvaiable(p.getAppointments(), pharmacy.getId());
			}
			catch(NullPointerException e) {
				appointmentFound = false;
			}
			
			if(!medicamentFound && !appointmentFound) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
		}
		
		Complaint c = new Complaint();
		c.setDeleted(false);
		c.setDescription(complaint.getDescription());
		c.setEmployee(employee);
		c.setPharmacy(pharmacy);
		c.setResponder(null);
		c.setResponse(null);
		
		c = complaintService.create(c);
		
		Set<Complaint> complaints = p.getComplaints();
		complaints.add(c);
		patientService.update(p);
		
		return new ResponseEntity<ComplaintDTO>(new ComplaintDTO(c.getId(), c.getDescription()), HttpStatus.CREATED);
		
	}

	@GetMapping(value="/getPharmacyComplaint/{id}")
	@PreAuthorize("hasAnyRole('PATIENT', 'SYSTEM_ADMIN')")
	public ResponseEntity<ComplaintDTO> getPharmacy(@PathVariable("id") Long id){
		Complaint complaint = complaintService.findOneWithPharmacy(id);
		
		return new ResponseEntity<ComplaintDTO>(new ComplaintDTO(complaint), HttpStatus.OK);
	}
	
	@GetMapping(value="/getEmployeeComplaint/{id}")
	@PreAuthorize("hasAnyRole('PATIENT', 'SYSTEM_ADMIN')")
	public ResponseEntity<ComplaintDTO> getEmployee(@PathVariable("id") Long id){
		Complaint complaint = complaintService.findOneWithEmployee(id);
		
		return new ResponseEntity<ComplaintDTO>(new ComplaintDTO(complaint), HttpStatus.OK);
	}
	
	@PostMapping(value="/update")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<ComplaintDTO> saveResponse(@RequestBody ComplaintDTO complaintDTO) throws Exception{
		
		AdminSystem admin = adminService.findOneWithCompalints(complaintDTO.getResponder());
		if (admin == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Patient patient = patientService.findOne(complaintDTO.getPatient());
		if (patient == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Complaint complaint = null;
		if(complaintDTO.getEmployee()!= null) {
			complaint = complaintService.findOneWithEmployee(complaintDTO.getId());
		}
		else {
			complaint = complaintService.findOneWithPharmacy(complaintDTO.getId());
		}
		
		if(complaint == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		System.out.println(complaint.getDescription()+ "   "+ complaint.getId());
		complaint.setResponder(admin);
		complaint.setPatient(patient);
		complaint.setResponse(complaintDTO.getResponse());
		
		Complaint saved = complaintService.update(complaint);
		
		Set<Complaint> responseComplaints = complaintService.getResponderComplaint(admin.getEmail());
		if(responseComplaints == null) {
			responseComplaints = new HashSet<Complaint>();
		}
		responseComplaints.add(saved);
		adminService.update(admin);
		
		
		emailService.sendComplaintAnswer(complaint);
		
		return new ResponseEntity<ComplaintDTO>(new ComplaintDTO(saved.getId(), saved.getDescription(), saved.getResponder().getEmail(), saved.getResponse()), HttpStatus.OK);
	}
	
	@GetMapping(value ="/getAllAnswered/{email}")
	@PreAuthorize("hasAnyRole('PATIENT', 'SYSTEM_ADMIN')")
	public ResponseEntity<Collection<ComplaintDTO>> getAllAnswered(@PathVariable("email") String email){
		Collection<Complaint> complaints = complaintService.findAllAnswered();
		Collection<ComplaintDTO> complaintsDTO = new ArrayList<ComplaintDTO>();
	
		for(Complaint c : complaints) {
			if(c.getResponder().getEmail().equalsIgnoreCase(email) || c.getPatient().getEmail().equalsIgnoreCase(email)) {
				ComplaintDTO dto = new ComplaintDTO(c);
				complaintsDTO.add(dto);
			}
		}
		
		return new ResponseEntity<>(complaintsDTO, HttpStatus.ACCEPTED);
	}
	@GetMapping(value ="/getAllUnanswered")
	@PreAuthorize("hasAnyRole('PATIENT', 'SYSTEM_ADMIN')")
	public ResponseEntity<Collection<ComplaintDTO>> getAllUnanswered(){
		Collection<Complaint> complaints = complaintService.findAllUnanswered();
		Collection<ComplaintDTO> complaintsDTO = new ArrayList<ComplaintDTO>();
		for(Complaint c : complaints) {
			if(c.getEmployee() != null) {
				ComplaintDTO dto = new ComplaintDTO(c);
				complaintsDTO.add(dto);
			}
			else {
				ComplaintDTO dto = new ComplaintDTO(c);
				complaintsDTO.add(dto);
			}
		}
		
		return new ResponseEntity<>(complaintsDTO, HttpStatus.ACCEPTED);
	}
	
	private boolean isPharmacyAvaiable(Set<Appointment> appointments, Long id) {
		boolean appointmentFound = false;
		
			for(Appointment a : appointments) {
				if(a.getType() == AppointmentType.COUNSELING) {
					Pharmacist pharmacist = pharmacistService.findOne(a.getChosenEmployee().getEmail());
					if(pharmacist.getPharmacy().getId() == id) {
						appointmentFound = true;
						break;
					}
				}
			}
		return appointmentFound;
		
		
	}
	
	private boolean isMedicamentAvaiable(Set<ePrescription> prescriptions, Long id) {
		boolean medicamentFound = false;
		
		for(ePrescription e : prescriptions) {
			if(e.getPharmacy().getId() == id) {
				medicamentFound = true;
				break;
			}
		}
		return medicamentFound;
		
	}
	
	private boolean isEmployeeAvaiable(String email, Set<Appointment> appointments) {
		boolean employeeFound = false;
		for(Appointment a : appointments) {
			if(a.getChosenEmployee().getEmail().equalsIgnoreCase(email) && a.isDone()) {
				employeeFound = true;
				break;
			}
		}
		
		return employeeFound;
	}
}
