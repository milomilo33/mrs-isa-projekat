package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class MedicalReport {
	private Long id;
	private String description;
	private LocalDateTime date;
	private HashMap<Long,PrescriptionMedicament> prescriptionMedicaments;
	private boolean deleted;
	
	public MedicalReport() {}
	
	public MedicalReport(Long id, String description, LocalDateTime date,
			HashMap<Long, PrescriptionMedicament> prescriptionMedicaments, boolean deleted) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
		this.prescriptionMedicaments = prescriptionMedicaments;
		this.deleted = deleted;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public HashMap<Long, PrescriptionMedicament> getPrescriptionMedicaments() {
		return prescriptionMedicaments;
	}
	public void setPrescriptionMedicaments(HashMap<Long, PrescriptionMedicament> prescriptionMedicaments) {
		this.prescriptionMedicaments = prescriptionMedicaments;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	

	

}
