package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.AppointmentDetailsDTO;
import com.mrsisa.mrsisaprojekat.dto.ReportTextDTO;
import com.mrsisa.mrsisaprojekat.model.*;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DermatologistService dermatologistService;

    
    @Autowired
    private PharmacistService pharmacistService;

	@GetMapping(value = "/{id}/start")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Long> startAppointment(@PathVariable("id") Long appointmentId) {
//		Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentEmployeeEmail;
		if (principal instanceof UserDetails) {
			currentEmployeeEmail = ((UserDetails)principal).getUsername();
		} else {
			currentEmployeeEmail = principal.toString();
		}

		Long medicalReportId = appointmentService.startAppointment(appointmentId, currentEmployeeEmail);

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

        
    @PostMapping(value="/{id}/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody Appointment appointment,@PathVariable("id") Long id, @PathVariable("email") String email) throws Exception {
    	Appointment savedAppointment = appointmentService.makeAppointmentDermatologist(appointment, id, email);
    	if(savedAppointment == null) {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
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
