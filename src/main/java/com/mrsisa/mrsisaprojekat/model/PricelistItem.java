package com.mrsisa.mrsisaprojekat.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class PricelistItem {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Price price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;
	
	public PricelistItem() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	

}
