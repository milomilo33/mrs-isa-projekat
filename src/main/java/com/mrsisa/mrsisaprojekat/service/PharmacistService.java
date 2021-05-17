package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Rating;

public interface PharmacistService {
	
	List<Pharmacist> findAll();
	
	Pharmacist findOne(String email);
	
	Pharmacist create(Pharmacist pharmacist) throws Exception;
	
	Pharmacist update(Pharmacist pharmacist) throws Exception;
	
	boolean delete(String email);

	List<Appointment> getAvailableAppointments(Pharmacist p);

	Pharmacist findOneCounselings(String email);

	Collection<Appointment> getDoneCounselingsWithPatientsForPharmacist(String email);
	
	Pharmacist getRatings(String email);

	void addRating(Rating rating, String ratedEmployeeEmail);

	Integer getRatingOfUser(String pharmacistEmail, String patientEmail);
  
	Pharmacist getOneWithAddress(String email);
	
	double getRating(String email);
}
