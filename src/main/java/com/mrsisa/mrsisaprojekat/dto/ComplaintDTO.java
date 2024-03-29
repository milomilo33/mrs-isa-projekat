package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Complaint;

public class ComplaintDTO {

	private String description;
	private Long id;
	private Long pharmacy;
	private String employee;
	private boolean deleted;
	private String responder;
	private String response;
	private String patient;
	
	public ComplaintDTO() {}

	public ComplaintDTO(Complaint complaint) {
		this.id = complaint.getId();
		this.description = complaint.getDescription();
		this.deleted = complaint.isDeleted();
		if(complaint.getEmployee() != null) {
			this.employee = complaint.getEmployee().getEmail();
		}else {
			this.employee = null;
		}
		if(complaint.getPharmacy() != null) {
			this.pharmacy = complaint.getPharmacy().getId();
		}
		else {
			this.pharmacy = null;
		}
		if(complaint.getResponder()!=null) {
			this.responder = complaint.getResponder().getEmail();
		}
		else {
			this.responder = null;
		}
		
		this.response = complaint.getResponse();
		if(complaint.getPatient() != null) {
			this.patient = complaint.getPatient().getEmail();
		}
		else {
			this.patient = null;
		}
		
	}
	
	public ComplaintDTO(Long id, String description, String responder, String employee, Long pharmacy, String response) {
		this.deleted = false;
		this.description = description;
		this.responder = responder;
		this.employee = employee;
		this.pharmacy = pharmacy;
		this.id = id;
		this.response = response;
		
	}
	
	public ComplaintDTO(String description, String employee) {
		this.deleted = false;
		this.description = description;
		this.employee = employee;
		this.pharmacy = null;
		this.response = null;
		this.responder = null;
	}
	public ComplaintDTO(Long id, String description, String employee, Long pharmacy) {
		this.deleted = false;
		this.id = id;
		this.description = description;
		this.employee = employee;
		this.pharmacy = pharmacy;
		this.response = null;
		this.responder = null;
	}
	public ComplaintDTO(Long id, String description, String responder, String response) {
		this.id = id;
		this.description = description;
		this.responder = responder;
		this.response = response;
	}
	public ComplaintDTO(String description, Long pharmacy) {
		this.deleted = false;
		this.description = description;
		this.pharmacy = pharmacy;
		this.employee = null;
		this.response = null;
		this.responder = null;
	}
	
	public ComplaintDTO(Long id, String description) {
		this.deleted = false;
		this.id = id;
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Long pharmacy) {
		this.pharmacy = pharmacy;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getResponder() {
		return responder;
	}

	public void setResponder(String responder) {
		this.responder = responder;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}
}
