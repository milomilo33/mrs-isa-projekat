package com.mrsisa.mrsisaprojekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class RequestMedicament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER/*, cascade= CascadeType.ALL*/)
	private Medicament medicament;
	
	@Column(name = "quantity", unique = false, nullable = false)
	private int quantity;
	
	@Column(name = "accepted", unique = false, nullable = false)
	private boolean accepted;
	
	@ManyToOne(fetch = FetchType.EAGER/*, cascade= CascadeType.ALL*/)
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private AdminPharmacy admin;

	public RequestMedicament() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public AdminPharmacy getAdmin() {
		return admin;
	}

	public void setAdmin(AdminPharmacy admin) {
		this.admin = admin;
	}

	

}
