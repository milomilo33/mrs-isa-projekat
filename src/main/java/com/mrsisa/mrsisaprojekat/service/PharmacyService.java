package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

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
}
