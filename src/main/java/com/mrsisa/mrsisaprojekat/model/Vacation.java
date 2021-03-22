package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

public class Vacation {
	private Long id;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private Employee employee;
	private boolean deleted;
	
	public Vacation() {}

	public Vacation(Long id, LocalDate dateFrom, LocalDate dateTo, Employee employee, boolean deleted) {
		super();
		this.id = id;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.employee = employee;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	
	

}
