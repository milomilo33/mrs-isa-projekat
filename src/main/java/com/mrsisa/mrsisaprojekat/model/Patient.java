package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Patient extends User {
	
	@Column(name = "loyaltyPoints", unique = false, nullable = false)
	private Integer loyaltyPoints;
	
	@Column(name = "penaltyPoints", unique = false, nullable = false)
	private Integer penaltyPoints;
	
	@Column(name = "category", unique = false, nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Complaint> complaints;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Medicament> allergies;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ePrescription> ePrescriptions;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Pharmacy> subscribedPharmacies;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PrescriptionMedicament> reservedMedicaments;

	public Patient() {}
	

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}


	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}


	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}


	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}


	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}


	public Set<Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}


	public Set<Complaint> getComplaints() {
		return complaints;
	}


	public void setComplaints(Set<Complaint> complaints) {
		this.complaints = complaints;
	}


	public Set<Medicament> getAllergies() {
		return allergies;
	}


	public void setAllergies(Set<Medicament> allergies) {
		this.allergies = allergies;
	}


	public Set<ePrescription> getePrescriptions() {
		return ePrescriptions;
	}


	public void setePrescriptions(Set<ePrescription> ePrescriptions) {
		this.ePrescriptions = ePrescriptions;
	}


	public Set<Pharmacy> getSubscribedPharmacies() {
		return subscribedPharmacies;
	}


	public void setSubscribedPharmacies(Set<Pharmacy> subscribedPharmacies) {
		this.subscribedPharmacies = subscribedPharmacies;
	}


	public Set<PrescriptionMedicament> getReservedMedicaments() {
		return reservedMedicaments;
	}

	public void setReservedMedicaments(Set<PrescriptionMedicament> reservedMedicaments) {
		this.reservedMedicaments = reservedMedicaments;
	}
}
