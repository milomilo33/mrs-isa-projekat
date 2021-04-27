package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.repository.MedicamentRepositoryDB;

@Service
public class MedicamentServiceImpl implements MedicamentService {

	@Autowired
	private MedicamentRepositoryDB medicamentRepository;
	
	@Override
	public Medicament getRatings(Long id) {
		return medicamentRepository.getRatings(id);
	}
	@Override
	public Collection<Medicament> findAll() {
		return medicamentRepository.findAll();
	}

	@Override
	public Medicament findOne(Long id) {
		return medicamentRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Medicament create(Medicament medicament) {
		return medicamentRepository.save(medicament);
	}

	@Override
	public Medicament update(Medicament medicament) {
		Medicament temp = medicamentRepository.findById(medicament.getId()).orElse(null);
		temp.setDeleted(false);
		temp.setAnnotations(medicament.getAnnotations());
		temp.setIssuanceMode(medicament.getIssuanceMode());
		temp.setManufacturer(medicament.getManufacturer());
		temp.setMedicamentForm(medicament.getMedicamentForm());
		temp.setName(medicament.getName());
		temp.setStructure(medicament.getStructure());
		temp.setRatings(medicament.getRatings());
		temp.setSubstituteMedicaments(medicament.getSubstituteMedicaments());
		temp.setType(medicament.getType());
		
		return medicamentRepository.save(temp);
	}

	@Override
	public void delete(Medicament medicament) {
		medicament.setDeleted(true);
		medicamentRepository.save(medicament);
		
	}
	@Override
	public Collection<Medicament> findAllWithName(String query) {
		return medicamentRepository.findAllWithName(query);
	}
	@Override
	public Collection<Medicament> findAllFilter(int mode, int form) {
		return medicamentRepository.findFilter(mode, form);
	}

}
