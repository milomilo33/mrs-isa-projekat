package com.mrsisa.mrsisaprojekat.dto;

import java.util.Set;


public class QRCodeDTO {

	
	private String patient;
	private String date;
	private Set<QRItemDTO> prescriptionMedicaments;
	
	public QRCodeDTO() {
		
	}
	
	public QRCodeDTO(String patient, String date, Set<QRItemDTO> medicaments ) {
		this.patient = patient;
		this.date = date;
		this.prescriptionMedicaments = medicaments;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Set<QRItemDTO> getPrescriptionMedicaments() {
		return prescriptionMedicaments;
	}

	public void setPrescriptionMedicaments(Set<QRItemDTO> prescriptionMedicaments) {
		this.prescriptionMedicaments = prescriptionMedicaments;
	}
	
	
}
