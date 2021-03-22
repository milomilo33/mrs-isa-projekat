package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Supplier extends User {
	private HashMap<Long,Offer> offers;
	private HashMap<Long,Request> requests;
	private HashMap<Long,Order> orders;
	private HashMap<Long,MedicamentItem> medicaments;
	
	public Supplier() {}
	
	
	public HashMap<Long, Offer> getOffers() {
		return offers;
	}


	public void setOffers(HashMap<Long, Offer> offers) {
		this.offers = offers;
	}


	public HashMap<Long, Request> getRequests() {
		return requests;
	}

	public void setRequests(HashMap<Long, Request> requests) {
		this.requests = requests;
	}
	

	public HashMap<Long, Order> getOrders() {
		return orders;
	}


	public void setOrders(HashMap<Long, Order> orders) {
		this.orders = orders;
	}


	public HashMap<Long, MedicamentItem> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(HashMap<Long, MedicamentItem> medicaments) {
		this.medicaments = medicaments;
	}
	
	

}
