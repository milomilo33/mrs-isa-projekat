package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.AppointmentDetailsDTO;
import com.mrsisa.mrsisaprojekat.dto.ReportTextDTO;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.model.Appointment.AppointmentType;
import com.mrsisa.mrsisaprojekat.service.AppointmentService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import com.mrsisa.mrsisaprojekat.service.PharmacistService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DermatologistService dermatologistService;

    @Autowired
    private PharmacyService pharmacyService;
    
    @Autowired
    private PharmacistService pharmacistService;

	@GetMapping(value = "/{id}/start")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Long> startAppointment(@PathVariable("id") Long appointmentId) {
		Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
		Long medicalReportId = appointmentService.startAppointment(appointmentId, employee.getEmail());

		if (medicalReportId == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(medicalReportId, HttpStatus.OK);
	}

    @GetMapping(value = "/{id}/absent")
    @PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
    public ResponseEntity<String> markPatientAbsence(@PathVariable("id") Long appointmentId) {

        Collection<Role> roles = (Collection<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isDermatologist = false;
        for (Role role : roles) {
            if (role.getName().equals("ROLE_DERMATOLOGIST")) {
                isDermatologist = true;
            }
        }

        // 1) provera da li odgovarajuci dermatolog/farmaceut salje ovaj zahtev
        boolean validAppointment = false;
        if (isDermatologist) {
            Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            validAppointment = dermatologistService.dermatologistHasAppointment(currentDermatologist.getEmail(), appointmentId);
        }
        else {
            // farmaceut
			Pharmacist currentPharmacist = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validAppointment = pharmacistService.pharmacistHasAppointment(currentPharmacist.getEmail(), appointmentId);
        }

        // 2) belezenje odsustva i zavrsavanje pregleda/savetovanja
        if (validAppointment) {
            boolean successful = appointmentService.markPatientAbsence(appointmentId);

            if (!successful) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

	@PostMapping(value = "/{id}/finish", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Object> finishAppointment(@PathVariable("id") Long appointmentId, @RequestBody ReportTextDTO body) {
		Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
		String reportText = body.getReportText();
		Boolean successful = appointmentService.finishAppointment(appointmentId, employee.getEmail(), reportText);

		if (successful == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (!successful) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

    public boolean check(Set<WorkHour> workHours, LocalDate date, LocalTime time1, LocalTime time2) {
    	for(WorkHour w : workHours) {
    		int val = time1.compareTo(w.getWorkHourFrom());
    		int val2 = w.getWorkHourTo().compareTo(time2);
    		if(w.getDay().ordinal()+1 == date.getDayOfWeek().getValue() && time1.compareTo(w.getWorkHourFrom())<0 &&
    				w.getWorkHourTo().compareTo(time2) >0 || time2.compareTo(w.getWorkHourFrom()) <0 ||
    				w.getDay().ordinal()+1 == date.getDayOfWeek().getValue() &&  w.getWorkHourFrom().compareTo(time1)>0 && w.getWorkHourTo().compareTo(time2)>0 && time2.compareTo(w.getWorkHourFrom())>0 ||	
    				w.getDay().ordinal()+1 == date.getDayOfWeek().getValue()  && time1.compareTo(w.getWorkHourFrom())>0 && time2.compareTo(w.getWorkHourTo())>0 && time1.compareTo(w.getWorkHourTo())<0 || 
    				w.getDay().ordinal()+1 != date.getDayOfWeek().getValue() ) {
				
    			return true;
			}
    	}
			
						
    	return  false;
    }
    public boolean checkAppointment(Set<Appointment> appointments, LocalDate date, LocalTime time1, LocalTime time2) {
    	for(Appointment a : appointments) {
    		if(!a.isDeleted()) {
    		int val = time1.compareTo(a.getTermFrom());
    		int val2 = time2.compareTo(a.getTermTo());
    		if(a.getDate().equals(date) && time1.compareTo(a.getTermFrom()) == 0
    				&& time2.compareTo(a.getTermTo()) == 0 ||a.getDate().equals(date) && a.getTermFrom().compareTo(time1)<=0 && a.getTermTo().compareTo(time2) >=0 && time2.compareTo(a.getTermFrom())<=0
    				|| a.getDate().equals(date) && a.getTermFrom().compareTo(time1)>=0 && a.getTermTo().compareTo(time2)>=0 && time2.compareTo(a.getTermFrom()) >=0 ||
    				a.getDate().equals(date)  && time1.compareTo(a.getTermFrom())>=0 &&  time2.compareTo(a.getTermTo())>=0 && time1.compareTo(a.getTermTo())<=0) {
				return true;
			}
    	}
    	
    	}
    	return  false;
    }
        
    @PostMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('PHARMACY_ADMIN)")
	public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody Appointment appointment,@PathVariable("id") Long id) throws Exception {
		Appointment a = new Appointment();
		a.setMedicalReport(null);
		a.setDeleted(false);
		a.setPatient(null);
		Dermatologist d = dermatologistService.findOne(appointment.getPatient().getEmail());
		if(d!= null) {
			if(check(d.getWorkHour(), appointment.getDate(), appointment.getTermFrom(),appointment.getTermTo())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if(checkAppointment(d.getMedicalExaminations(), appointment.getDate(), appointment.getTermFrom(), appointment.getTermTo())) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			d.setRatings(null);
			d.setRequests(null);
			a.setChosenEmployee(d);
			a.setDate(appointment.getDate());
			a.setTermFrom(appointment.getTermFrom());
			a.setTermTo(appointment.getTermTo());
			a.setType(AppointmentType.EXAMINATION);
		}
		
	
		Appointment savedAppointment = appointmentService.create(a);
		Pharmacy p = pharmacyService.findOneWithAppointments(id);
		p.getAppointments().add(savedAppointment);
		pharmacyService.update(p);
		return new ResponseEntity<>(new AppointmentDTO(savedAppointment), HttpStatus.OK);
    }

	@GetMapping(value = "/{id}/details")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<AppointmentDetailsDTO> getAppointmentDetails(@PathVariable("id") Long appointmentId) {
    	AppointmentDetailsDTO details = appointmentService.getAppointmentDetails(appointmentId);

    	if (details == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

    	return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/pharmacy")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Pharmacy> getPharmacyOfAppointment(@PathVariable("id") Long appointmentId) {
		Pharmacy pharmacy = appointmentService.getPharmacyOfAppointment(appointmentId);

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(pharmacy, HttpStatus.OK);
	}
}
