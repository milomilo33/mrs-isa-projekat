package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

import javax.persistence.*;


@Entity
public class Vacation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="dateFrom", unique=false, nullable=false)
	private LocalDate dateFrom;
	
	@Column(name="dateTo", unique=false, nullable=false)
	private LocalDate dateTo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee employee;
	
	@Column(name="deleted", unique=false, nullable=false)
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
