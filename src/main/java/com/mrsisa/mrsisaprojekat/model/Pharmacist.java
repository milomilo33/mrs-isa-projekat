package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
public class Pharmacist extends Employee {
	
	// potencijalno kaskadiranje
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Appointment> counselings;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.MERGE)
	private Pharmacy pharmacy;
	
	public Pharmacist() {}

	public Set<Appointment> getCounselings() {
		return counselings;
	}

	public void setCounselings(Set<Appointment> counselings) {
		this.counselings = counselings;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
	
	

}
