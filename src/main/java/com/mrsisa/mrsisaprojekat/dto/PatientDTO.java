package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;

import java.util.ArrayList;

public class PatientDTO {

	private String email;
	private String password;
	private String name;
	private String lastName;
	private String phoneNumber;
	private AddressDTO address;
	private ArrayList<Appointment> appointments;
	private ArrayList<PrescriptionMedicamentDTO> medicaments;
	
	public PatientDTO() {
		
	}
	
	public PatientDTO(Patient patient) {
		this.email = patient.getEmail();
		this.password = patient.getPassword();
		this.name = patient.getName();
		this.lastName = patient.getLastName();
		this.phoneNumber = patient.getPhoneNumber();
		this.address = new AddressDTO(patient.getAddress());
		this.appointments = new ArrayList<>(patient.getAppointments());
		this.medicaments = new ArrayList<>();
		for (PrescriptionMedicament m : patient.getReservedMedicaments()) {
			this.medicaments.add(new PrescriptionMedicamentDTO(m));
		}
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
