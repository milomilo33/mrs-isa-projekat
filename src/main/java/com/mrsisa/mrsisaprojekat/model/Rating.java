package com.mrsisa.mrsisaprojekat.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "value", unique = false, nullable = false)
	private int value;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Patient patient;

	@Version
	@ColumnDefault("0")
	private short version;

	public short getVersion() {
		return version;
	}
	
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
