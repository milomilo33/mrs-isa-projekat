package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.ePrescription;

public interface ePrescriptionService {
	List<ePrescription> findAll();
	
	ePrescription findOne(Long id);
	
	ePrescription create(ePrescription ePrescription) throws Exception;
	
	ePrescription update(ePrescription ePrescription) throws Exception;
	
	boolean delete(Long id);
	
	boolean dispensePrescription(Long id, Pharmacist pharmacist);
	
	ePrescription findPrescriptionForPharmacist(Long id, Pharmacist pharmacist);
}
