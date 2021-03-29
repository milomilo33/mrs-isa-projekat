package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.*;

@Entity
public class PricelistItemMedicament extends PricelistItem {

	@ManyToOne(fetch = FetchType.LAZY)
	private Medicament medicament;
	
	public PricelistItemMedicament() {}



	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

}
