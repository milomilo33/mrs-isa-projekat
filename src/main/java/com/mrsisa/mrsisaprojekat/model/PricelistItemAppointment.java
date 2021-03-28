package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class PricelistItemAppointment extends PricelistItem {
	
	@OneToOne(fetch = FetchType.LAZY)
	private Appointment appointment;
	
	public PricelistItemAppointment() {}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}


}
