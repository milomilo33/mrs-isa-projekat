package com.mrsisa.mrsisaprojekat.dto;

public class AppointmentCalendarDTO {
    private String patientName;
    private String patientLastName;
    private Long appointmentId;
    private String dateStartStr;
    private String dateEndStr;
    private String pharmacyName;
    private String status;

    public AppointmentCalendarDTO(String patientName, String patientLastName, Long appointmentId, String dateStartStr, String dateEndStr, String pharmacyName, String status) {
        this.patientName = patientName;
        this.patientLastName = patientLastName;
        this.appointmentId = appointmentId;
        this.dateStartStr = dateStartStr;
        this.dateEndStr = dateEndStr;
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

    public String getDateStartStr() {
        return dateStartStr;
    }

    public void setDateStartStr(String dateStartStr) {
        this.dateStartStr = dateStartStr;
    }

    public String getDateEndStr() {
        return dateEndStr;
    }

    public void setDateEndStr(String dateEndStr) {
        this.dateEndStr = dateEndStr;
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
