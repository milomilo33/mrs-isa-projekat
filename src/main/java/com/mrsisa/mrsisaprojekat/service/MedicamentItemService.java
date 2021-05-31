package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;

@Service
public interface MedicamentItemService {
	
	Collection<MedicamentItem> findAll();
	
	MedicamentItem findOne(Long id);
	
	MedicamentItem create(MedicamentItem medicamentItem) throws Exception;
	
	MedicamentItem update(MedicamentItem medicamentItem) throws Exception;
	
	void delete(Long id);
	
	boolean find(Medicament m);
	
	void restore(Long id);

	void deleteMedicament(MedicamentItem mi);

}
