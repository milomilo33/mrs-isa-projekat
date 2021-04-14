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
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	// purchased/taken/dispensed
	@Column(name = "done", unique = false, nullable = false)
	private boolean done;
	
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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	
}
