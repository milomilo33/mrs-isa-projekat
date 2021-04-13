package com.mrsisa.mrsisaprojekat.dto;

import java.util.ArrayList;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;


public class PharmacistDTO {
	
	
	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	private PharmacyDTO pharmacy;
	private ArrayList<WorkHourDTO> workHours;
	public PharmacistDTO() {}

	public PharmacistDTO(Pharmacist pharmacist) {
		this(pharmacist.getEmail(),pharmacist.getPassword(),pharmacist.getName(),pharmacist.getLastName(),pharmacist.getPhoneNumber(),new AddressDTO(pharmacist.getAddress()),new PharmacyDTO(pharmacist.getPharmacy()));
	}
	public PharmacistDTO(String email,String password,String name,String lastName,String phoneNumber,AddressDTO address,PharmacyDTO pharmacy) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.lastName = lastName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.pharmacy = pharmacy;
	}
	
	public PharmacistDTO(String email,String password,String name,String lastName,String phoneNumber,AddressDTO address,PharmacyDTO pharmacy, ArrayList<WorkHourDTO> workHours) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.lastName = lastName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.pharmacy = pharmacy;
		this.workHours = workHours;
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

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setWorkHours(ArrayList<WorkHourDTO> workHours) {
		this.workHours = workHours;
	}
	
	

	
	
	

}
