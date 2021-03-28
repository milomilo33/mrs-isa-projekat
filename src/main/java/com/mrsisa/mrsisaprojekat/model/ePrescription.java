package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ePrescription {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	@Column(name = "date", unique = false, nullable = false)
	private LocalDate date;
	
	// mozda treba kaskadiranje
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PrescriptionMedicament> prescriptionMedicaments;
	
	public ePrescription() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Set<PrescriptionMedicament> getPrescriptionMedicaments() {
		return prescriptionMedicaments;
	}

	public void setPrescriptionMedicaments(Set<PrescriptionMedicament> prescriptionMedicaments) {
		this.prescriptionMedicaments = prescriptionMedicaments;
	}

	
}
