package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Appointment;

import java.time.LocalTime;

public class AppointmentDTO {
    private String patientEmail;
    private Long appointmentId;

    public AppointmentDTO(String patientEmail, Long appointmentId) {
        this.patientEmail = patientEmail;
        this.appointmentId = appointmentId;
    }

//    public AppointmentDTO()

    public AppointmentDTO() {

    }

    public AppointmentDTO(Appointment a) {
        this(a.getPatient().getEmail(), a.getId());
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
}
