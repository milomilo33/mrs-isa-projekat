package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Complaint {
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee employee;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private AdminSystem responder;
	
	public Complaint() {}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public AdminSystem getResponder() {
		return responder;
	}

	public void setResponder(AdminSystem responder) {
		this.responder = responder;
	}

}
