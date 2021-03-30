package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Pharmacist;

public interface PharmacistService {
	
	List<Pharmacist> findAll();
	
	Pharmacist findOne(String email);
	
	Pharmacist create(Pharmacist pharmacist) throws Exception;
	
	Pharmacist update(Pharmacist pharmacist) throws Exception;
	
	void delete(String email);

}
