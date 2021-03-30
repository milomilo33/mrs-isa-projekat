package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.repository.PharmacistRepositoryDB;

@Service
public class PharmacistServiceImpl  implements PharmacistService {

	@Autowired
	private PharmacistRepositoryDB pharmacistRepository;
	
	@Override
	public List<Pharmacist> findAll() {
		List<Pharmacist> pharmacists = pharmacistRepository.getAllWithAddress();
		return pharmacists;
	}

	@Override
	@Transactional
	public Pharmacist findOne(String email) {
		Pharmacist pharmacist = pharmacistRepository.findById(email).orElseGet(null);
		return pharmacist;
	}

	@Override
	@Transactional
	public Pharmacist create(Pharmacist pharmacist) throws Exception {
		/*if(findOne(pharmacist.getEmail()) != null) {
			return null;
		}*/
		Pharmacist savedPharmacist = pharmacistRepository.save(pharmacist);
		return savedPharmacist;
	}

	@Override
	public Pharmacist update(Pharmacist pharmacist) throws Exception {
		Pharmacist pharmacistToUpdate = pharmacistRepository.findById(pharmacist.getEmail()).orElseGet(null);
		if (pharmacistToUpdate == null) {
			return null;
		}
		pharmacistRepository.save(pharmacist);
		return pharmacist;
	}

	@Override
	public void delete(String email) {
		// potrebno je proveriti da li ima zakazana savetovanja, ukoliko ih ima ne moze da se obrise
		pharmacistRepository.deleteById(email);
		
	}

}
