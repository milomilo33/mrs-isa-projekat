package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pharmacist extends Employee {
	
	@Column(name = "workHourFrom", unique = false, nullable = false)
	private LocalTime workHourFrom;
	
	@Column(name = "workHourTo", unique = false, nullable = false)
	private LocalTime workHourTo;
	
	// potencijalno kaskadiranje
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Appointment> counselings;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;
	
	public Pharmacist() {}

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



	public Set<Appointment> getCounselings() {
		return counselings;
	}

	public void setCounselings(Set<Appointment> counselings) {
		this.counselings = counselings;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	
	
	
	

}
