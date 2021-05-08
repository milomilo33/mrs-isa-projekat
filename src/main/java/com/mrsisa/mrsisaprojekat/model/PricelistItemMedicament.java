package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class PricelistItemMedicament  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Price> price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;

	@ManyToOne(fetch = FetchType.LAZY)
	private Medicament medicament;
	
	public PricelistItemMedicament() {}



	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}
	
	public Set<Price> getPrice() {
		return price;
	}

	public void setPrice(Set<Price> price) {
		this.price = price;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	

}
