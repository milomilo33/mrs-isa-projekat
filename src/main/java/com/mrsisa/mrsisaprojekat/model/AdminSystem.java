package com.mrsisa.mrsisaprojekat.model;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminSystem extends User {
	
	private HashMap<Employee,ArrayList<Vacation>> vacations;
	private HashMap<Long,Medicament> medicamentDatabase;
	private HashMap<Long,Complaint> complaints;
	
	public AdminSystem() {}

	
	public HashMap<Employee, ArrayList<Vacation>> getVacations() {
		return vacations;
	}


	public void setVacations(HashMap<Employee, ArrayList<Vacation>> vacations) {
		this.vacations = vacations;
	}


	public HashMap<Long, Complaint> getComplaints() {
		return complaints;
	}
	public void setComplaints(HashMap<Long, Complaint> complaints) {
		this.complaints = complaints;
	}


	public HashMap<Long, Medicament> getMedicamentDatabase() {
		return medicamentDatabase;
	}


	public void setMedicamentDatabase(HashMap<Long, Medicament> medicamentDatabase) {
		this.medicamentDatabase = medicamentDatabase;
	}
	
	
	
	
	

}
