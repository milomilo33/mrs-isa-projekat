package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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
	
	@Column(name = "takenDate", unique = false, nullable = true)
	private LocalDate takenDate;
	
	@Column(name = "price", unique = false, nullable = true)
	private double price;

	public ePrescription(Patient patient, LocalDate date, Set<PrescriptionMedicament> prescriptionMedicaments, Pharmacy pharmacy, LocalDate takenDate, double price) {
		this.patient = patient;
		this.date = date;
		this.prescriptionMedicaments = prescriptionMedicaments;
		this.pharmacy = pharmacy;
		this.done = false;
		this.deleted = false;
		this.takenDate = takenDate;
		this.price = price;
	}
	
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

	public LocalDate getTakenDate() {
		return takenDate;
	}

	public void setTakenDate(LocalDate takenDate) {
		this.takenDate = takenDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	

	
}
