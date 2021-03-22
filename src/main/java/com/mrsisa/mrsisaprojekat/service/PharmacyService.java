package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public interface PharmacyService {
	
	Collection<Pharmacy> findAll();
	
	Pharmacy findOne(Long id);
	
	Pharmacy create(Pharmacy pharmacy) throws Exception;
	
	Pharmacy update(Pharmacy phamracy) throws Exception;
	
	void delete(Long id);
}
