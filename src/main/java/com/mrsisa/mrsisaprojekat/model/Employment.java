package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Employment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "workHourFrom", unique = false, nullable = false)
	private LocalTime workHourFrom;
	
	@Column(name = "workHourTo", unique = false, nullable = false)
	private LocalTime workHourTo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Dermatologist dermatologist;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	public Employment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getWorkHourFrom() {
		return workHourFrom;
	}

	public void setWorkHourFrom(LocalTime workHourFrom) {
		this.workHourFrom = workHourFrom;
	}

	public LocalTime getWorkHourTo() {
		return workHourTo;
	}

	public void setWorkHourTo(LocalTime workHourTo) {
		this.workHourTo = workHourTo;
	}

	public Dermatologist getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(Dermatologist dermatologist) {
		this.dermatologist = dermatologist;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
