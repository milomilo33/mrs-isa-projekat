package com.mrsisa.mrsisaprojekat.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Dermatologist extends Employee {
	
	// potencijalno kaskadiranje
	@OneToMany(mappedBy = "chosenEmployee", fetch = FetchType.LAZY, cascade= CascadeType.MERGE)
	private Set<Appointment> medicalExaminations;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "dermatologists_pharmacies",
			   joinColumns = @JoinColumn(name = "dermatologist_id", 
			   							 referencedColumnName = "email"), 
			   inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
	private Set<Pharmacy> pharmacies;
	
	public Dermatologist() {}

	public Set<Appointment> getMedicalExaminations() {
		return medicalExaminations;
	}

	public void setMedicalExaminations(Set<Appointment> medicalExaminations) {
		this.medicalExaminations = medicalExaminations;
	}

	public Set<Pharmacy> getPharmacy() {
		return pharmacies;
	}

	public void setPharmacy(Set<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public Set<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(Set<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
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
