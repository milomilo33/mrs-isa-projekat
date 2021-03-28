package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order_t")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "status", unique = false, nullable = false)
	private OrderStatus status;
	
	@Column(name = "deadline", unique = false, nullable = false)
	private LocalDateTime deadline;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AdminPharmacy admin;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private Set<Offer> offers;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<MedicamentItem> medicamentItems;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	public Order() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public AdminPharmacy getAdmin() {
		return admin;
	}

	public void setAdmin(AdminPharmacy admin) {
		this.admin = admin;
	}

	
	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Set<MedicamentItem> getMedicamentItems() {
		return medicamentItems;
	}

	public void setMedicamentItems(Set<MedicamentItem> medicamentItems) {
		this.medicamentItems = medicamentItems;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	
	
	

}
