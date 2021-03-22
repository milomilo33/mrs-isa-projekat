package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;
import java.util.HashMap;

public class ePrescription {
	private Long id;
	private String patientEmail;
	private LocalDate date;
	private HashMap<Long,PrescriptionMedicament>prescirpionMedicaments;
	
	public ePrescription() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public HashMap<Long, PrescriptionMedicament> getPrescirpionMedicaments() {
		return prescirpionMedicaments;
	}

	public void setPrescirpionMedicaments(HashMap<Long, PrescriptionMedicament> prescirpionMedicaments) {
		this.prescirpionMedicaments = prescirpionMedicaments;
	}

	
}
