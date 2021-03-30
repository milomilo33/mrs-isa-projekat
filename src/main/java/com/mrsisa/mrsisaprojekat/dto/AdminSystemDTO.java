package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.AdminSystem;

public class AdminSystemDTO {
	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	
	public AdminSystemDTO() {
		
	}
	
	public AdminSystemDTO(AdminSystem admin) {
		this(admin.getEmail(), admin.getPassword(), admin.getName(), admin.getLastName(), admin.getPhoneNumber(), new AddressDTO(admin.getAddress()));
	}
	
	public AdminSystemDTO(String email, String password, String name, String lastName, String phoneNumber, AddressDTO address) {
		this.email = email;
		this.password = password;
		this.lastName = lastName;
		this.name = name;
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public AddressDTO getAddress() {
		return address;
	}
	
}
