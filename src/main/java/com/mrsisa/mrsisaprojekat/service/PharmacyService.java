package com.mrsisa.mrsisaprojekat.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mrsisa.mrsisaprojekat.model.*;

public interface PharmacyService {
	
	List<Pharmacy> findAll();
	
	Pharmacy findOne(Long id);
	
	Pharmacy create(Pharmacy pharmacy) throws Exception;
	
	Pharmacy update(Pharmacy phamracy) throws Exception;
	
	void delete(Long id);
	
	List<MedicamentItem> getAllMedicaments(Long id);
	
	
	Pharmacy findOneWithMedicaments(Long id);
	
	Pharmacy findOneWithDermatologists(Long id);
	
	Pharmacy findOneWithPharmacists(Long id);
	
	Pharmacy findOneWithAppointments(Long id);

	int getRating(Long id);
	
	ArrayList<Appointment> findAllAppointmentsDeramtologist(String email, Long id);

    void addRating(Rating rating, Long ratedEntityId);

    Integer getRatingOfUser(Long id, String email);
}
