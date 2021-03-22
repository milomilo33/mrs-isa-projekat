package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Appointment {
	private Long id;
	private LocalDate date;
	private LocalTime termFrom;
	private LocalTime termTo;
	private MedicalReport medicalReport;
	private boolean deleted;
	
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
	
	
	

}
