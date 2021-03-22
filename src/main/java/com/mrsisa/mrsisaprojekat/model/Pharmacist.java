package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalTime;
import java.util.HashMap;

public class Pharmacist extends Employee {
	
	private LocalTime workHourFrom;
	private LocalTime workHourTo;
	private HashMap<Long,Counseling>counselings;
	private Pharmacy pharmacy;
	
	public Pharmacist() {}

	public LocalTime getWorkHourFrom() {
		return workHourFrom;
	}



	public void setWorkHourFrom(LocalTime workHourFrom) {
		this.workHourFrom = workHourFrom;
	}



	public LocalTime getWorkHourTo() {
		return workHourTo;
	}



	public void setWorkHourTo(LocalTime workHourTo) {
		this.workHourTo = workHourTo;
	}



	public HashMap<Long, Counseling> getCounselings() {
		return counselings;
	}



	public void setCounselings(HashMap<Long, Counseling> counselings) {
		this.counselings = counselings;
	}



	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	
	
	
	

}
