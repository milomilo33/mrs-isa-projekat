package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.Patient;

public class PatientDTO {

	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	
	public PatientDTO() {
		
	}
	
	public PatientDTO(Patient patient) {
		this(patient.getEmail(), patient.getPassword(), patient.getName(), patient.getLastName(), patient.getPhoneNumber(), new AddressDTO(patient.getAddress()));
	}
	
	public PatientDTO(String email, String password, String name, String lastName, String phoneNumber, AddressDTO address) {
		this.email = email;
		this.password = password;
		this.lastName = lastName;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
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
