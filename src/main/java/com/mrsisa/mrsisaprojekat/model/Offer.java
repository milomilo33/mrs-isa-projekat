package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;

public class Offer {
	private Long id;
	private OfferStatus status;
	private double totalPrice;
	private LocalDateTime deadline;
	private Supplier supplier;
	private Order order;
	
	public Offer() {}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public OfferStatus getStatus() {
		return status;
	}


	public void setStatus(OfferStatus status) {
		this.status = status;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public LocalDateTime getDeadline() {
		return deadline;
	}


	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}


	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	
	

}
