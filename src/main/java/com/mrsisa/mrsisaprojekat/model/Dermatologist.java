package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Dermatologist extends Employee {
	private HashMap<Long,MedicalExamination>medicalExaminations;
	private HashMap<Long,Pharmacy>pharmacy;
	
	public Dermatologist() {}

	

	public HashMap<Long, MedicalExamination> getMedicalExaminations() {
		return medicalExaminations;
	}



	public void setMedicalExaminations(HashMap<Long, MedicalExamination> medicalExaminations) {
		this.medicalExaminations = medicalExaminations;
	}



	public HashMap<Long, Pharmacy> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(HashMap<Long, Pharmacy> pharmacy) {
		this.pharmacy = pharmacy;
	}

	

}
