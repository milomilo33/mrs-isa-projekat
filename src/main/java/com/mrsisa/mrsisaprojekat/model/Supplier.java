package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Supplier extends User {
	
	// potencijalno kaskadiranje
	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
	private Set<Offer> offers;
	
	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
	private Set<Order> orders;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<MedicamentItem> medicamentItems;
	
	public Supplier() {}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<MedicamentItem> getMedicaments() {
		return medicamentItems;
	}

	public void setMedicaments(Set<MedicamentItem> medicaments) {
		this.medicamentItems = medicaments;
	}	

}
