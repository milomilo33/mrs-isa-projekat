package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rating {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "value", unique = false, nullable = false)
	private int value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	public Rating() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	
	

}
