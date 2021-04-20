package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface DermatologistService {
	List<Dermatologist> findAll();
	
	Dermatologist findOne(String email);
	
	Dermatologist create(Dermatologist dermatologist) throws Exception;
	
	Dermatologist update(Dermatologist dermatologist) throws Exception;
	
	boolean delete(String email);

	Collection<Appointment> getUpcomingExaminationsForDermatologist(String email);

	boolean dermatologistHasAppointment(String email, Long appointmentId);

	List<Appointment> getAvailableAppointments(Dermatologist m);
}
