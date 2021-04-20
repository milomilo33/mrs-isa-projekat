package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class PrescriptionMedicament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// da li ovo i deleted treba u ePrescription umesto ovde?
	@Column(name = "purchased", unique = false, nullable = false)
	private boolean purchased;
	
	@Column(name = "expiryDate", unique = false, nullable = false)
	private LocalDate expiryDate;
	
	@Column(name = "quantity", unique = false, nullable = false)
	private int quantity;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Medicament medicament;
	
	public PrescriptionMedicament() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	@Override
	public String toString() {
		return "PrescriptionMedicament{" +
				"id=" + id +
				", purchased=" + purchased +
				", expiryDate=" + expiryDate +
				", quantity=" + quantity +
				", deleted=" + deleted +
				", medicament=" + medicament +
				'}';
	}
}
