package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Address;

public class AddressDTO {

	private Long id;
	private String country;
	private String city;
	private String street;
	private int number;
	private double latitude;
	private double longitude;
	
	public AddressDTO() {
		
	}
	
	public AddressDTO(Address address) {
		this(address.getId(), address.getCountry(), address.getCity(), address.getStreet(), address.getNumber());
	}
	
	public AddressDTO(Long id, String country, String city, String street, int number) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.street = street;
		this.number = number;
		
	}

	public AddressDTO(Long id, String country, String city, String street, int number, double latitude, double longitude) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.street = street;
		this.number = number;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public int getNumber() {
		return number;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
