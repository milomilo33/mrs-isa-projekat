package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.AppointmentPoints;

public interface AppointmentPointsService {

	Collection<AppointmentPoints> findAll();
	
	AppointmentPoints findOne(Long id);
	
	AppointmentPoints update(AppointmentPoints appointmentPoints);
}
