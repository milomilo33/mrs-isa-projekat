package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;

public class AdminPharmacyDTO {

	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	private PharmacyDTO pharmacy;
	
	public AdminPharmacyDTO() {
		
	}
	
	public AdminPharmacyDTO(AdminPharmacy admin) {
		this(admin.getEmail(), admin.getPassword(), admin.getName(), admin.getLastName(), admin.getPhoneNumber(), new AddressDTO(admin.getAddress()), new PharmacyDTO(admin.getPharmacy()));
	}
	
	public AdminPharmacyDTO(String email, String password, String name, String lastName, String phoneNumber, AddressDTO address, PharmacyDTO pharmacy) {
		this.email = email;
		this.password = password;
		this.lastName = lastName;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.pharmacy = pharmacy;
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

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}


	
}

