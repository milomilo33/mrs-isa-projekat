package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalDateTime;

import com.mrsisa.mrsisaprojekat.model.Offer;

public class OfferDTO {
	
	private Long id;
	
	private String status;
	
	private double totalPrice;
	
	private LocalDateTime deadline;
	
	private String supplier;
	
	private OrderDTO order;
	
	public OfferDTO() {}
	
	public OfferDTO(Offer offer) {
		this.id = offer.getId();
		this.deadline = offer.getDeadline();
		this.status = offer.getStatus().name();
		this.totalPrice = offer.getTotalPrice();
		this.supplier = offer.getSupplier().getEmail();
		this.order = new OrderDTO(offer.getOrder());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	
}
