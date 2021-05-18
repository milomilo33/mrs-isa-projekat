package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalDateTime;

public class AppointmentCalendarDTO {
    private String patientName;
    private String patientLastName;
    private Long appointmentId;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String pharmacyName;
    private String status;

    public AppointmentCalendarDTO(String patientName, String patientLastName, Long appointmentId, LocalDateTime dateStart, LocalDateTime dateEnd, String pharmacyName, String status) {
        this.patientName = patientName;
        this.patientLastName = patientLastName;
        this.appointmentId = appointmentId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.pharmacyName = pharmacyName;
        this.status = status;
    }

    public AppointmentCalendarDTO() {}

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
