package com.mrsisa.mrsisaprojekat.repository;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public interface PharmacyRepository {

	Collection<Pharmacy> findAll();
	
	Pharmacy create(Pharmacy pharmacy);
	
	Pharmacy findOne(Long id);
	
	Pharmacy update(Pharmacy pharmacy);
	
	void delete(Long id);
}
