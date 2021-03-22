package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;

@Service
public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;
	
	@Override
	public Collection<Pharmacy> findAll() {
		Collection<Pharmacy> pharmacies = pharmacyRepository.findAll();
		return pharmacies;
	}

	@Override
	public Pharmacy findOne(Long id) {
		Pharmacy pharmacy = pharmacyRepository.findOne(id);
		return pharmacy;
	}

	@Override
	public Pharmacy create(Pharmacy pharmacy) throws Exception {
		if(pharmacy.getId() != null) {
			throw new Exception("Id mora biti null!");
		}
		Pharmacy savedPharmacy = pharmacyRepository.create(pharmacy);
		return savedPharmacy;
	}

	@Override
	public Pharmacy update(Pharmacy pharmacy) throws Exception {
		Pharmacy pharmacyToUpdate = findOne(pharmacy.getId());
		if (pharmacyToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		pharmacyRepository.update(pharmacy);
		return pharmacy;
	}

	@Override
	public void delete(Long id) {
		pharmacyRepository.delete(id);
		
	}

}
