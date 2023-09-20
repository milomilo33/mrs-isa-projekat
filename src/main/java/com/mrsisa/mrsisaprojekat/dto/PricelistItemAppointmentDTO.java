package com.mrsisa.mrsisaprojekat.dto;

import java.util.ArrayList;

import com.mrsisa.mrsisaprojekat.model.Appointment.AppointmentType;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;

public class PricelistItemAppointmentDTO {

	private Long id;
	private PharmacyDTO pharmacy;
	private ArrayList<Price> price;
	private AppointmentType appointment;
	
	
	public PricelistItemAppointmentDTO() {
		
	}

	public PricelistItemAppointmentDTO(PricelistItemAppointment pricelistItem) {
		this(pricelistItem.getId(), new PharmacyDTO(pricelistItem.getPharmacy()),pricelistItem.getAppointment());
	}


	public PricelistItemAppointmentDTO(Long id, PharmacyDTO pharmacy, AppointmentType appointment) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.appointment = appointment;
	}

	public Long getId() {
		return id;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}


	public ArrayList<Price> getPrice() {
		return price;
	}

	public AppointmentType getApointment() {
		return appointment;
	}

	public void setPrice(ArrayList<Price> price) {
		this.price = price;
	}
	

	
	

}

