package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "dateFrom", unique = false, nullable = false)
	private LocalDate dateFrom;
	
	@Column(name = "dateTo", unique = false, nullable = false)
	private LocalDate dateTo;
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@Column(name = "promotion", unique = false, nullable = false)
	private boolean promotion; // true promotion, false action
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	public Promotion() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
