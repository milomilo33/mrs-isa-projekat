package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.*;

import com.mrsisa.mrsisaprojekat.model.Appointment.AppointmentType;

@Entity
public class PricelistItemAppointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Price> price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;

	private AppointmentType appointment;
	
	public PricelistItemAppointment() {}


	public AppointmentType getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentType appointment) {
		this.appointment = appointment;
	}
	public Set<Price> getPrice() {
		return price;
	}

	public void setPrice(Set<Price> price) {
		this.price = price;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}


}
