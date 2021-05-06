package com.mrsisa.mrsisaprojekat.dto;

import java.util.ArrayList;

import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;

public class PricelistItemMedicamentDTO {
	
	private Long id;
	private PharmacyDTO pharmacy;
	private Price price;
	private MedicamentDTO medicament;
	
	
	public PricelistItemMedicamentDTO() {
		
	}

	public PricelistItemMedicamentDTO(PricelistItemMedicament pricelistItem) {
		this(pricelistItem.getId(), new PharmacyDTO(pricelistItem.getPharmacy()),pricelistItem.getPrice(),new MedicamentDTO(pricelistItem.getMedicament()));
	}


	public PricelistItemMedicamentDTO(Long id, PharmacyDTO pharmacy,Price price, MedicamentDTO medicament) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.price = price;
		this.medicament = medicament;
	}

	public Long getId() {
		return id;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}


	public Price getPrice() {
		return price;
	}

	public MedicamentDTO getMedicament() {
		return medicament;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	

	
	

}
