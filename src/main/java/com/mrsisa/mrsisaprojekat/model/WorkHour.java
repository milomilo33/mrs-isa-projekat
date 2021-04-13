package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class WorkHour {
	
	public enum Day{
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "day", unique = false, nullable = false)
	private Day day;
	
	@Column(name = "workHourFrom", unique = false)
	private LocalTime workHourFrom;
	
	@Column(name = "workHourTo", unique = false)
	private LocalTime workHourTo;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.MERGE)
	private Pharmacy pharmacy;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	public WorkHour() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
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
