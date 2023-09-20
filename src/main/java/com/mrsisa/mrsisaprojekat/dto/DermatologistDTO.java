package com.mrsisa.mrsisaprojekat.dto;

import java.util.ArrayList;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;

public class DermatologistDTO {

	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	private ArrayList<Appointment> appointments;
	private ArrayList<WorkHourDTO> workHours;
	private ArrayList<PharmacyDTO> pharmacies;
	private ArrayList<AppointmentDTO> allAppointments;
	private double rating;

	public DermatologistDTO() {}

	public DermatologistDTO(Dermatologist dermatologist) {
		this.email = dermatologist.getEmail();
		this.password = dermatologist.getPassword();
		this.name = dermatologist.getName();
		this.lastName = dermatologist.getLastName();
		this.phoneNumber = dermatologist.getPhoneNumber();
		this.address = new AddressDTO(dermatologist.getAddress());
		//this.appointments = new ArrayList<>(dermatologist.getMedicalExaminations());
	}
	
	public DermatologistDTO(String email, String password, String name, String lastName, String phoneNumber, AddressDTO address, ArrayList<Appointment> appointments) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.lastName = lastName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		//this.appointments = appointments;
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

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}

	public ArrayList<AppointmentDTO> getAllAppointments() {
		return allAppointments;
	}

	public void setAllAppointments(ArrayList<AppointmentDTO> allAppointments) {
		this.allAppointments = allAppointments;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
	
}
