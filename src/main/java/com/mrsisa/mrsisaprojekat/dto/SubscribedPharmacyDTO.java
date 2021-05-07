package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public class SubscribedPharmacyDTO {

	private String user;
	private Pharmacy pharmacy;
	
	public SubscribedPharmacyDTO() {}
	public SubscribedPharmacyDTO(String email, Pharmacy pharmacy) {
		this.user = email;
		this.pharmacy = pharmacy;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Pharmacy getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
	
	
}
