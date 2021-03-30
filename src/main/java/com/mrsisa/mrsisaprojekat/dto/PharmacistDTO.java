package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalTime;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;


public class PharmacistDTO {
	
	
	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	private LocalTime workHourFrom;
	private LocalTime workHourTo;
	private PharmacyDTO pharmacy;
	
	public PharmacistDTO() {}

	public PharmacistDTO(Pharmacist pharmacist) {
		this(pharmacist.getEmail(),pharmacist.getPassword(),pharmacist.getName(),pharmacist.getLastName(),pharmacist.getPhoneNumber(),new AddressDTO(pharmacist.getAddress()),pharmacist.getWorkHourFrom(),pharmacist.getWorkHourTo(),new PharmacyDTO(pharmacist.getPharmacy()));
	}
	
	public PharmacistDTO(String email,String password,String name,String lastName,String phoneNumber,AddressDTO address,LocalTime workHourFrom,LocalTime workHourTo,PharmacyDTO pharmacy) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.lastName = lastName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.workHourFrom = workHourFrom;
		this.workHourTo = workHourTo;
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

	public LocalTime getWorkHourFrom() {
		return workHourFrom;
	}

	public LocalTime getWorkHourTo() {
		return workHourTo;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}
	

	
	
	

}
