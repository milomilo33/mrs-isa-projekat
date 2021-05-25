package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalDate;

import com.mrsisa.mrsisaprojekat.model.Request;

public class RequestDTO {
	private Long id;
	
	private String description;
	
	private LocalDate dateFrom;
	
	private LocalDate dateTo;
	
	private boolean accepted;

	private String rejectionReason;

	private EmployeeDTO employee;
	
	private boolean deleted;
	
	public RequestDTO() {}

	public RequestDTO(Request request) {
		this.id = request.getId();
		this.dateFrom = request.getDateFrom();
		this.dateTo = request.getDateTo();
		this.accepted = request.isAccepted();
		this.rejectionReason = request.getRejectionReason();
		this.employee = new EmployeeDTO(request.getEmployee());
		this.deleted = request.isDeleted();
	}
	

	public RequestDTO(Long id, String description, LocalDate dateFrom, LocalDate dateTo, boolean accepted,
			String rejectionReason, EmployeeDTO employee, boolean deleted) {
		super();
		this.id = id;
		this.description = description;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.accepted = accepted;
		this.rejectionReason = rejectionReason;
		this.employee = employee;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
