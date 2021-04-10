package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.transaction.annotation.Transactional;

@Entity
public class Dermatologist extends Employee {
	
	// potencijalno kaskadiranje
	@OneToMany(fetch = FetchType.LAZY)
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
	

}
