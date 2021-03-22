package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

public class Request {
	private Long id;
	private String description;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private boolean accepted;
	private boolean deleted;
	
	public Request() {}
	
	public Request(Long id, String description, LocalDate dateFrom, LocalDate dateTo, boolean accepted,
			boolean deleted) {
		super();
		this.id = id;
		this.description = description;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.accepted = accepted;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	

}
