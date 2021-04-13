package com.mrsisa.mrsisaprojekat.dto;

import java.util.ArrayList;

import com.mrsisa.mrsisaprojekat.model.Dermatologist;

public class DermatologistDTO {

	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	private ArrayList<WorkHourDTO> workHours;
	private ArrayList<PharmacyDTO> pharmacies;
	
	public DermatologistDTO() {}

	public DermatologistDTO(Dermatologist dermatologist) {
		this(dermatologist.getEmail(), dermatologist.getPassword(), dermatologist.getName(), dermatologist.getLastName(), dermatologist.getPhoneNumber(),new AddressDTO(dermatologist.getAddress()));
	}
	
	public DermatologistDTO(String email, String password, String name, String lastName, String phoneNumber, AddressDTO address) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.lastName = lastName;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}
	public DermatologistDTO(String email, String password, String name, String lastName, String phoneNumber, AddressDTO address, ArrayList<WorkHourDTO> workHours , ArrayList<PharmacyDTO> pharmacies) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.lastName = lastName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.workHours = workHours;
		this.pharmacies = pharmacies;
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

	public ArrayList<WorkHourDTO> getWorkHours() {
		return workHours;
	}

	public void setWorkHours(ArrayList<WorkHourDTO> workHours) {
		this.workHours = workHours;
	}

	public ArrayList<PharmacyDTO> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(ArrayList<PharmacyDTO> pharmacies) {
		this.pharmacies = pharmacies;
	}
	
	

	
}
