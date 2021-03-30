package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public class PharmacyDTO {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private AddressDTO address; 

	public PharmacyDTO() {}

	public PharmacyDTO(Pharmacy pharmacy) {
		this(pharmacy.getId(),pharmacy.getName(),pharmacy.getDescription(),new AddressDTO(pharmacy.getAddress()));
	}
	
	
	public PharmacyDTO(Long id, String name,String description,AddressDTO address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
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

}
