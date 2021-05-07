package com.mrsisa.mrsisaprojekat.dto;

import java.util.HashSet;
import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Order;

public class OrderDTO {

	private Long id;
	private Set<MedicamentItemDTO> medicamentItems;
	
	public OrderDTO() {}
	
	public OrderDTO(Order order) {
		this.id = order.getId();
		this.medicamentItems = new HashSet<MedicamentItemDTO>();
		for (MedicamentItem mi : order.getMedicamentItems()) {
				medicamentItems.add(new MedicamentItemDTO(mi));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<MedicamentItemDTO> getMedicamentItems() {
		return medicamentItems;
	}

	public void setMedicamentItems(Set<MedicamentItemDTO> medicamentItems) {
		this.medicamentItems = medicamentItems;
	}

	
	
}
