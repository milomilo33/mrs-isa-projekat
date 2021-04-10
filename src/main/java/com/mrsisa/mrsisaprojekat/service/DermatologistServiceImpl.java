package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.repository.DermatologistRepositoryDB;

@Service
public class DermatologistServiceImpl implements DermatologistService {

	@Autowired
	private DermatologistRepositoryDB dermatologistRepository;
	
	@Override
	public List<Dermatologist> findAll() {
		List<Dermatologist> dermatologists = dermatologistRepository.getAllWithAddress();
		return dermatologists;
	}

	@Override
	public Dermatologist findOne(String email) {
		Dermatologist dermatologist = dermatologistRepository.getOneDermatologist(email);
		return dermatologist;
	}

	@Override
	public Dermatologist create(Dermatologist dermatologist) throws Exception {
		Dermatologist savedDermatologist = dermatologistRepository.save(dermatologist);
		return savedDermatologist;
	}

	@Override
	public Dermatologist update(Dermatologist dermatologist) throws Exception {
		Dermatologist dermatologistToUpdate = dermatologistRepository.findById(dermatologist.getEmail()).orElseGet(null);
		if (dermatologistToUpdate == null) {
			return null;
		}
		dermatologistRepository.save(dermatologist);
		return dermatologist;
	}

	@Override
	public boolean delete(String email) {
		// TODO Auto-generated method stub
		return false;
	}


}
