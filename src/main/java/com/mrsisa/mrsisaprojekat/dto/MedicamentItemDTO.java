package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;

public class MedicamentItemDTO {
	
	private Long id;
	private MedicamentDTO medicament;
	private int quantity;
	private boolean deleted;
	
	
	public MedicamentItemDTO() {
		
	}

	public MedicamentItemDTO(MedicamentItem medicamentItem) {
		this(medicamentItem.getId(), new MedicamentDTO(medicamentItem.getMedicament()),medicamentItem.getQuantity(),medicamentItem.isDeleted());
	}


	public MedicamentItemDTO(Long id, MedicamentDTO medicament, int quantity, boolean deleted) {
		super();
		this.id = id;
		this.medicament = medicament;
		this.quantity = quantity;
		this.deleted = deleted;
	}


	public Long getId() {
		return id;
	}


	public MedicamentDTO getMedicament() {
		return medicament;
	}


	public int getQuantity() {
		return quantity;
	}


	public boolean isDeleted() {
		return deleted;
	}
	
	
}
