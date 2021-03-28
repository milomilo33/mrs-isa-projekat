package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Appointment {
	
	public enum AppointmentType {
		COUNSELING,
		EXAMINATION
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "date", unique = false, nullable = false)
	private LocalDate date;
	
	@Column(name = "termFrom", unique = false, nullable = false)
	private LocalTime termFrom;
	
	@Column(name = "termTo", unique = false, nullable = false)
	private LocalTime termTo;
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	private MedicalReport medicalReport;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	@Column(name = "type", unique = false, nullable = false)
	private AppointmentType type;
	
	public Appointment() {}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public MedicalReport getMedicalReport() {
		return medicalReport;
	}

	public void setMedicalReport(MedicalReport medicalReport) {
		this.medicalReport = medicalReport;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}
	
	
	

}
