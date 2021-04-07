package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Medicament;

public interface MedicamentService {

	Collection<Medicament> findAll();
	
	Medicament findOne(Long id);
	
	Medicament create(Medicament medicament);
	
	Medicament update(Medicament medicament);
	
	void delete(Medicament medicament);

	Medicament getRatings(Long id);
	
}
