package com.mrsisa.mrsisaprojekat.model;

public class Address {
	private String street, city,country;
	private int number;
	
	public Address() {}

	public Address(String street, String city, String country, int number) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
		this.number = number;
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
