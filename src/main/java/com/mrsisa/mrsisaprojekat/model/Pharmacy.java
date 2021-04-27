package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.transaction.annotation.Transactional;

@Entity
public class Pharmacy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.MERGE)

	private Address address; 
	
	@Column(name = "income", unique = false, nullable = false)
	private double income;
	
	@ManyToMany(mappedBy = "pharmacies", fetch = FetchType.EAGER, cascade= CascadeType.MERGE)
	private Set<Dermatologist> dermatologists;
	
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY)
	private Set<Pharmacist> pharmacists;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Order> orders;
	
	@OneToMany(fetch = FetchType.LAZY,cascade= CascadeType.ALL)
	private Set<MedicamentItem> medicamentItems;
	
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PricelistItem> pricelist;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Promotion> promotions;
	
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY)
	private Set<AdminPharmacy> admins;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Appointment> appointments;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Vacation> vacations;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Request> requests;
	
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


	public Set<Dermatologist> getDermatologists() {
		return dermatologists;
	}


	public void setDermatologists(Set<Dermatologist> dermatologists) {
		this.dermatologists = dermatologists;
	}


	public Set<Pharmacist> getPharmacists() {
		return pharmacists;
	}


	public void setPharmacists(Set<Pharmacist> pharmacists) {
		this.pharmacists = pharmacists;
	}


	public Set<MedicamentItem> getMedicamentItems() {
		return medicamentItems;
	}


	public void setMedicamentItems(Set<MedicamentItem> medicamentItems) {
		this.medicamentItems = medicamentItems;
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


	public Set<PricelistItem> getPricelist() {
		return pricelist;
	}


	public void setPricelist(Set<PricelistItem> pricelist) {
		this.pricelist = pricelist;
	}


	public Set<Rating> getRatings() {
		return ratings;
	}


	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}


	public Set<Promotion> getPromotions() {
		return promotions;
	}


	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}


	public Set<AdminPharmacy> getAdmins() {
		return admins;
	}


	public void setAdmins(Set<AdminPharmacy> admins) {
		this.admins = admins;
	}


	public Set<Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}


	public Set<Vacation> getVacations() {
		return vacations;
	}


	public void setVacations(Set<Vacation> vacations) {
		this.vacations = vacations;
	}


	public Set<Request> getRequests() {
		return requests;
	}


	public void setRequests(Set<Request> requests) {
		this.requests = requests;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	
	

}
