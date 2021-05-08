package com.mrsisa.mrsisaprojekat.dto;

import java.util.ArrayList;

import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;

public class PricelistItemMedicamentDTO {
	
	private Long id;
	private PharmacyDTO pharmacy;
	private ArrayList<Price> price;
	private MedicamentDTO medicament;
	
	
	public PricelistItemMedicamentDTO() {
		
	}

	public PricelistItemMedicamentDTO(PricelistItemMedicament pricelistItem) {
		this(pricelistItem.getId(), new PharmacyDTO(pricelistItem.getPharmacy()),new MedicamentDTO(pricelistItem.getMedicament()));
	}


	public PricelistItemMedicamentDTO(Long id, PharmacyDTO pharmacy, MedicamentDTO medicament) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		//this.price = price;
		this.medicament = medicament;
	}

	public Long getId() {
		return id;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}


	public ArrayList<Price> getPrice() {
		return price;
	}

	public MedicamentDTO getMedicament() {
		return medicament;
	}

	public void setPrice(ArrayList<Price> price) {
		this.price = price;
	}
	

	
	

}
