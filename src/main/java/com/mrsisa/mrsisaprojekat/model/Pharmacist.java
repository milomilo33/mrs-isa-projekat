package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Pharmacist extends Employee {
	
	// potencijalno kaskadiranje
	@OneToMany(mappedBy = "chosenEmployee", fetch = FetchType.LAZY)
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
