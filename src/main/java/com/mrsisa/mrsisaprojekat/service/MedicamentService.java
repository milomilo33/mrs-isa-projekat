package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.MedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Medicament;

import java.util.Collection;

public interface MedicamentService {

	Collection<Medicament> findAll();
	
	Medicament findOne(Long id);
	
	Medicament create(Medicament medicament);
	
	Medicament update(Medicament medicament);
	
	void delete(Medicament medicament);

	Medicament getRatings(Long id);

	Collection<Medicament> findAllWithName(String query);

	Collection<Medicament> findAllFilter(int mode, int form);

	Collection<MedicamentDTO> getNonallergicSubstituteMedicinesForPatientInPharmacyWithQuantity(
								Long medicamentId, String patientEmail, int quantity, Long pharmacyId);
}
