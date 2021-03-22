package com.mrsisa.mrsisaprojekat.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Pharmacy {
	private Long id;
	private String name,description;
	private Address address;
	private double income;
	private HashMap<String,Dermatologist>dermatologists;
	private HashMap<String,Pharmacist> pharmacists;
	private HashMap<Long,Order> orders;
	private HashMap<Long,MedicamentItem> medicaments;
	private Pricelist pricelist;
	private ArrayList<Evaluation> evaluations;
	private HashMap<Long,Promotion>promotions;
	private HashMap<String,AdminPharmacy> admins;
	private HashMap<Long,Appointment> appointments;
	private boolean deleted;
	
	public Pharmacy() {}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	public double getIncome() {
		return income;
	}


	public void setIncome(double income) {
		this.income = income;
	}


	public HashMap<String, Dermatologist> getDermatologists() {
		return dermatologists;
	}

	public void setDermatologists(HashMap<String, Dermatologist> dermatologists) {
		this.dermatologists = dermatologists;
	}

	public HashMap<String, Pharmacist> getPharmacists() {
		return pharmacists;
	}

	public void setPharmacists(HashMap<String, Pharmacist> pharmacists) {
		this.pharmacists = pharmacists;
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

	

	public Pricelist getPricelist() {
		return pricelist;
	}


	public void setPricelist(Pricelist pricelist) {
		this.pricelist = pricelist;
	}


	public ArrayList<Evaluation> getEvaluations() {
		return evaluations;
	}


	public void setEvaluations(ArrayList<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}


	public HashMap<Long, Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(HashMap<Long, Promotion> promotions) {
		this.promotions = promotions;
	}


	public HashMap<String, AdminPharmacy> getAdmins() {
		return admins;
	}


	public void setAdmins(HashMap<String, AdminPharmacy> admins) {
		this.admins = admins;
	}


	public HashMap<Long, Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(HashMap<Long, Appointment> appointments) {
		this.appointments = appointments;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	
	

}
