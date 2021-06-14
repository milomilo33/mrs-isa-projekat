package com.mrsisa.mrsisaprojekat.dto;

public class AppointmentWithStringDatesDTO {
    private String patientEmail;
    private Long appointmentId;
	private String date;
	private String termFrom;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTermFrom() {
		return termFrom;
	}

	public void setTermFrom(String termFrom) {
		this.termFrom = termFrom;
	}

	public String getTermTo() {
		return termTo;
	}

	public void setTermTo(String termTo) {
		this.termTo = termTo;
	}

	private String termTo;
	private String type;
	private EmployeeDTO employee;

    public AppointmentWithStringDatesDTO() {

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
