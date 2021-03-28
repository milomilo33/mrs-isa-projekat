package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "street", unique = false, nullable = false)
	private String street;
	
	@Column(name = "city", unique = false, nullable = false)
	private String city;
	
	@Column(name = "country", unique = false, nullable = false)
	private String country;
	
	@Column(name = "number", unique = false, nullable = false)
	private int number;
	
	public Address() {}

	public Address(String street, String city, String country, int number) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
		this.number = number;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}


}
