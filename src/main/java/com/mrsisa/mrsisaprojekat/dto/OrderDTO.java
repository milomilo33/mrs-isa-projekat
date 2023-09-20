package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Order;

public class OrderDTO {

	
	private Long id;
	private Set<MedicamentItemDTO> medicamentItems;
	private LocalDate deadline;
	private String status;
	private AdminPharmacyDTO admin;
	private Set<OfferDTO> offers;
	
	public OrderDTO() {}
	
	public OrderDTO(Order order) {
		this.id = order.getId();
		this.medicamentItems = new HashSet<MedicamentItemDTO>();
		for (MedicamentItem mi : order.getMedicamentItems()) {
				medicamentItems.add(new MedicamentItemDTO(mi));
		}
		this.deadline = order.getDeadline();
		this.status = order.getStatus().name();
		this.admin = new AdminPharmacyDTO(order.getAdmin());
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

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AdminPharmacyDTO getAdmin() {
		return admin;
	}

	public void setAdmin(AdminPharmacyDTO admin) {
		this.admin = admin;
	}

	public Set<OfferDTO> getOffers() {
		return offers;
	}

	public void setOffers(Set<OfferDTO> offers) {
		this.offers = offers;
	}
	
	
}
