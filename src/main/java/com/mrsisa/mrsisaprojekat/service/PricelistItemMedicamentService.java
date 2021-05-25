package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;


@Service
public interface PricelistItemMedicamentService {

	Collection<PricelistItemMedicament> findAllPharmacy(Long id);
	
	Collection<PricelistItemMedicament> findAllMeds(Long id);
	
	PricelistItemMedicament findOneInPharmacy(Long medicamentId, Long pharmacyId);
	
	PricelistItemMedicament findOne(Long id);
	
	PricelistItemMedicament create(PricelistItemMedicament pricelistItem) throws Exception;
	
	PricelistItemMedicament update(PricelistItemMedicament pricelistItem) throws Exception;
	
	void delete(Long id);
	
	void restore(Long id);
	
	PricelistItemMedicament findOnePricelistItemMedicament(Long id);

	Set<PricelistItemMedicament> findPharmacyForMedicament(Long id);
	
	void checkPromotions(Set<PricelistItemMedicament>pricelistItems)  throws Exception;
	
	PricelistItemMedicament findByPharmacyAndMed(Long id, Long pId);
	

}
