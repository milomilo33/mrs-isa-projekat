package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalDate;
import java.util.Map;

public class ePrescriptionDispenseDTO {
	private Long id;
	private String patientEmail;
	private Map<String, Integer> medicineQuantity;
	private LocalDate expiryDate;
	public ePrescriptionDispenseDTO() {}
	public ePrescriptionDispenseDTO(Long id, String patientEmail, Map<String, Integer> medicineQuantity,
			LocalDate expiryDate) {
		super();
		this.id = id;
		this.patientEmail = patientEmail;
		this.medicineQuantity = medicineQuantity;
		this.expiryDate = expiryDate;
	}
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
	public Map<String, Integer> getMedicineQuantity() {
		return medicineQuantity;
	}
	public void setMedicineQuantity(Map<String, Integer> medicineQuantity) {
		this.medicineQuantity = medicineQuantity;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
}
