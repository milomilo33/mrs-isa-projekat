package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Appointment.AppointmentType;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Employee;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.Role;
import com.mrsisa.mrsisaprojekat.model.WorkHour;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean check(Set<WorkHour> workHours, LocalDate date, LocalTime time1, LocalTime time2) {
    	for(WorkHour w : workHours) {
    		int val = time1.compareTo(w.getWorkHourFrom());
    		int val2 = w.getWorkHourTo().compareTo(time2);
    		if(w.getDay().ordinal()+1 == date.getDayOfWeek().getValue() && val>0
    				&& val2>0) {
				return true;
			}
    	}
    	return  false;
    }
    public boolean checkAppointment(Set<Appointment> appointments, LocalDate date, LocalTime time1, LocalTime time2) {
    	for(Appointment a : appointments) {
    		int val = time1.compareTo(a.getTermFrom());
    		int val2 = time2.compareTo(a.getTermTo());
    		int val3 = time2.compareTo(a.getTermFrom());
    		if(a.getDate() == date && val == 0
    				&& val2 == 0 || a.getDate() == date && val<0 && val2 <0 && val3>0
    				|| a.getDate() == date && val>0 && val2>0 && val3 <0) {
				return true;
			}
    	}
    	return  false;
    }
        
    @PostMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('PHARMACY_ADMIN)")
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment,@PathVariable("id") Long id) throws Exception {
		Appointment a = new Appointment();
		a.setMedicalReport(null);
		a.setDeleted(false);
		a.setPatient(null);
		Dermatologist d = dermatologistService.findOne(appointment.getPatient().getEmail());
		if(d!= null) {
			if(!check(d.getWorkHour(), appointment.getDate(), appointment.getTermFrom(),appointment.getTermTo())) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			if(checkAppointment(d.getMedicalExaminations(), appointment.getDate(), appointment.getTermFrom(), appointment.getTermTo())) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			d.setRatings(null);
			d.setRequests(null);
			a.setChosenEmployee(d);
    	
		}else {
			Pharmacist p = pharmacistService.findOne(appointment.getPatient().getEmail());
			if(!check(p.getWorkHour(), appointment.getDate(), appointment.getTermFrom(),appointment.getTermTo())) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			if(checkAppointment(p.getCounselings(), appointment.getDate(), appointment.getTermFrom(), appointment.getTermTo())) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			p.setRatings(null);
			p.setRequests(null);
			a.setChosenEmployee(p);
		}
		a.setDate(appointment.getDate());
		a.setTermFrom(appointment.getTermFrom());
		a.setTermTo(appointment.getTermTo());
		if(appointment.getType().name().equals("EXAMINATION")){
			a.setType(AppointmentType.EXAMINATION);
		}else {
			a.setType(AppointmentType.COUNSELING);
		}
		Appointment savedAppointment = appointmentService.create(a);
		Pharmacy p = pharmacyService.findOneWithAppointments(id);
		p.getAppointments().add(savedAppointment);
		pharmacyService.update(p);
		return new ResponseEntity<>(savedAppointment,HttpStatus.OK);
    }
}
