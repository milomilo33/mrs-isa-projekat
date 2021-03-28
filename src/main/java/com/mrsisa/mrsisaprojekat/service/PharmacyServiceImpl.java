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
		Pharmacy pharmacy = pharmacyRepository.findById(id).orElseGet(null);
		return pharmacy;
	}

	@Override
	public Pharmacy create(Pharmacy pharmacy) throws Exception {
		if(pharmacy.getId() != null) {
			return null;
		}
		Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
		return savedPharmacy;
	}

	@Override
	public Pharmacy update(Pharmacy pharmacy) throws Exception {
		Pharmacy pharmacyToUpdate = findOne(pharmacy.getId());
		if (pharmacyToUpdate == null) {
			return null;
		}
		pharmacyRepository.save(pharmacy);
		return pharmacy;
	}

	@Override
	public void delete(Long id) {
		pharmacyRepository.deleteById(id);
		
	}

}
