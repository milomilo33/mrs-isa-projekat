package com.mrsisa.mrsisaprojekat.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Patient extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "loyaltyPoints", unique = false, nullable = false)
	private Integer loyaltyPoints;
	
	@Column(name = "penaltyPoints", unique = false, nullable = false)
	private Integer penaltyPoints;
	
	@Column(name = "category", unique = false, nullable = false)
	private Category category;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return getRoles();
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return getEmail();
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
