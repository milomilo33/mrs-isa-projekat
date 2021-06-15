package com.mrsisa.mrsisaprojekat.dto;

import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public class PharmacyDTO {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private AddressDTO address; 
	
	private double cost;
	
	private int rating;
	
	private Set<String> admins; 

	public PharmacyDTO() {}

	public PharmacyDTO(Pharmacy pharmacy) {
		this(pharmacy.getId(),pharmacy.getName(),pharmacy.getDescription(),new AddressDTO(pharmacy.getAddress()));
		address.setLatitude(pharmacy.getAddress().getLatitude());
		address.setLongitude(pharmacy.getAddress().getLongitude());
	}
	
	
	public PharmacyDTO(Long id, String name,String description,AddressDTO address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
	}
	
	public PharmacyDTO(Long id, String name, String description, double cost, AddressDTO address, int rating) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.address = address;
		this.rating = rating;
	}
	
	public PharmacyDTO(Long id, String name, AddressDTO address, Set<String> admins) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.admins = admins;
	}
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Set<String> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<String> admins) {
		this.admins = admins;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	

}
