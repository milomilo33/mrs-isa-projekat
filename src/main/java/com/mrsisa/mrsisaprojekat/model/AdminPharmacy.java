package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AdminPharmacy extends User {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;
	
	public AdminPharmacy() {}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
}
