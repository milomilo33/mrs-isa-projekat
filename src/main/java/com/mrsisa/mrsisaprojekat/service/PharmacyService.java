package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public interface PharmacyService {
	
	List<Pharmacy> findAll();
	
	Pharmacy findOne(Long id);
	
	Pharmacy create(Pharmacy pharmacy) throws Exception;
	
	Pharmacy update(Pharmacy phamracy) throws Exception;
	
	void delete(Long id);
}
