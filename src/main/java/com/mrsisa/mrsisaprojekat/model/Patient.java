package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Patient extends User {
	private int points;
	private Category category;
	private HashMap<Long,PrescriptionMedicament> prescriptionMedicaments;
	private HashMap<Long,Appointment> appointments;
	private HashMap<Long,Complaint>complaints;
	private HashMap<Long,Medicament> allergies;
	private HashMap<Long,ePrescription> ePrescriptions;
	
	public Patient() {}

	

	public int getPoints() {
		return points;
	}



	public void setPoints(int points) {
		this.points = points;
	}



	
	

	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public HashMap<Long, PrescriptionMedicament> getPrescriptionMedicaments() {
		return prescriptionMedicaments;
	}



	public void setPrescriptionMedicaments(HashMap<Long, PrescriptionMedicament> prescriptionMedicaments) {
		this.prescriptionMedicaments = prescriptionMedicaments;
	}



	public HashMap<Long, Appointment> getAppointments() {
		return appointments;
	}



	public void setAppointments(HashMap<Long, Appointment> appointments) {
		this.appointments = appointments;
	}



	public HashMap<Long, Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(HashMap<Long, Complaint> complaints) {
		this.complaints = complaints;
	}


	public HashMap<Long, Medicament> getAllergies() {
		return allergies;
	}

	public void setAllergies(HashMap<Long, Medicament> allergies) {
		this.allergies = allergies;
	}



	public HashMap<Long, ePrescription> getePrescriptions() {
		return ePrescriptions;
	}



	public void setePrescriptions(HashMap<Long, ePrescription> ePrescriptions) {
		this.ePrescriptions = ePrescriptions;
	}


	
	
}
