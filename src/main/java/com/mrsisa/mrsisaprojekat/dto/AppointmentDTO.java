package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
public class AppointmentDTO {
    private String patientEmail;
    private Long appointmentId;
	private LocalDate date;
	private LocalTime termFrom;
	private LocalTime termTo;
	private String type;
	private EmployeeDTO employee;

    public AppointmentDTO(String patientEmail, Long appointmentId) {
        this.patientEmail = patientEmail;
        this.appointmentId = appointmentId;
    }
    
    public AppointmentDTO(String patientEmail, Long appointmentId, LocalDate date, LocalTime termFrom, LocalTime termTo, String type, EmployeeDTO e) {
    	this.patientEmail = patientEmail;
        this.appointmentId = appointmentId;
        this.date = date;
        this.termFrom = termFrom;
        this.termTo = termTo;
        this.type = type;
        this.employee = e;
    }
    public AppointmentDTO() {

    }

    public AppointmentDTO(Appointment a) {
    	if(a.getPatient()!= null) {
    	 this.patientEmail = a.getPatient().getEmail();
    	}else {
    		this.patientEmail = null;
    	}
    	this.appointmentId = a.getId();
        this.date = a.getDate();
        this.termFrom = a.getTermFrom();
        this.termTo = a.getTermTo();
        this.type = a.getType().name();
        this.employee = new EmployeeDTO(a.getChosenEmployee());
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTermFrom() {
		return termFrom;
	}

	public void setTermFrom(LocalTime termFrom) {
		this.termFrom = termFrom;
	}

	public LocalTime getTermTo() {
		return termTo;
	}

	public void setTermTo(LocalTime termTo) {
		this.termTo = termTo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}


}
