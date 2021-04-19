package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Role;
import com.mrsisa.mrsisaprojekat.service.AppointmentService;
import com.mrsisa.mrsisaprojekat.service.DermatologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value="/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DermatologistService dermatologistService;

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
}
