package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;

@Service

public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;
	
	@Override
	public List<Pharmacy> findAll() {
		List<Pharmacy> pharmacies = pharmacyRepository.getAllWithAddress();
		return pharmacies;
	}

	@Override
	public Pharmacy findOne(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOnePharmacy(id);
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
		Pharmacy pharmacyToUpdate = pharmacyRepository.findById(pharmacy.getId()).orElseGet(null);
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

	@Override
	public List<MedicamentItem> getAllMedicaments(Long id) {
		List<MedicamentItem> items = pharmacyRepository.getAllMedicaments(id);
		if(items == null) {
			return null;
		}
		return items;
	}

	@Override
	public Pharmacy findOneWithMedicaments(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithMedicaments(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}

}
