package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class WorkCalendar {
	private HashMap<Long,Appointment> appointments;
	
	public WorkCalendar() {}

	public HashMap<Long, Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(HashMap<Long, Appointment> appointments) {
		this.appointments = appointments;
	}

	
	

}
