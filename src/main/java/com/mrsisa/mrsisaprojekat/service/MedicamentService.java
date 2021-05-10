package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.Rating;

public interface MedicamentService {

	Collection<Medicament> findAll();
	
	Medicament findOne(Long id);
	
	Medicament create(Medicament medicament);
	
	Medicament update(Medicament medicament);
	
	void delete(Medicament medicament);

	Medicament getRatings(Long id);

	Collection<Medicament> findAllWithName(String query);

	Collection<Medicament> findAllFilter(int mode, int form);

    void addRating(Rating rating, Long id);

    Integer getRatingOfUser(Long id, String email);
}
