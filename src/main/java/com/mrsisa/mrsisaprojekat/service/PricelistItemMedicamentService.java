package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.PricelistItem;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;


@Service
public interface PricelistItemMedicamentService {

	Collection<PricelistItemMedicament> findAllPharmacy(Long id);
	
	PricelistItemMedicament findOne(Long id);
	
	PricelistItemMedicament create(PricelistItemMedicament pricelistItem) throws Exception;
	
	PricelistItemMedicament update(PricelistItemMedicament pricelistItem) throws Exception;
	
	void delete(Long id);
	
	void restore(Long id);
	
	PricelistItemMedicament findOnePricelistItemMedicament(Long id);

}