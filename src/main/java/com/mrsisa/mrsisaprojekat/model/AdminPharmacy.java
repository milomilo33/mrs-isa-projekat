package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class AdminPharmacy extends User {
	private HashMap<Long,Vacation> vacations;
	private HashMap<Long,Request> requests;
	private Pharmacy pharmacy;
	
	public AdminPharmacy() {}
	
	
	public HashMap<Long, Vacation> getVacations() {
		return vacations;
	}


	public void setVacations(HashMap<Long, Vacation> vacations) {
		this.vacations = vacations;
	}


	public HashMap<Long, Request> getRequests() {
		return requests;
	}

	public void setRequests(HashMap<Long, Request> requests) {
		this.requests = requests;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}


	
	

}
