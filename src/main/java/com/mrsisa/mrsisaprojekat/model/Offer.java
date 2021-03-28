package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "status", unique = false, nullable = false)
	private OfferStatus status;
	
	@Column(name = "totalPrice", unique = false, nullable = false)
	private double totalPrice;
	
	@Column(name = "deadline", unique = false, nullable = false)
	private LocalDateTime deadline;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@ManyToOne(fetch = FetchType.LAZY)
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
