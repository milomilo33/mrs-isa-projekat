package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Order {
	private Long id;
	private OrderStatus status;
	private LocalDateTime deadline;
	private AdminPharmacy admin;
	private HashMap<Long,Offer> offers;
	private HashMap<Long,MedicamentItem> medicaments;
	private Supplier supplier;
	private HashMap<Long,Pharmacy> pharmacy;
	private boolean deleted;
	
	public Order() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public AdminPharmacy getAdmin() {
		return admin;
	}

	public void setAdmin(AdminPharmacy admin) {
		this.admin = admin;
	}

	

	public HashMap<Long, Offer> getOffers() {
		return offers;
	}

	public void setOffers(HashMap<Long, Offer> offers) {
		this.offers = offers;
	}

	public HashMap<Long, MedicamentItem> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(HashMap<Long, MedicamentItem> medicaments) {
		this.medicaments = medicaments;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}



	public HashMap<Long, Pharmacy> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(HashMap<Long, Pharmacy> pharmacy) {
		this.pharmacy = pharmacy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	
	
	

}
