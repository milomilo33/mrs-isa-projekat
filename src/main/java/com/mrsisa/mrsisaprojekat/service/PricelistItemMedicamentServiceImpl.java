package com.mrsisa.mrsisaprojekat.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;
import com.mrsisa.mrsisaprojekat.repository.PricelistItemMedicamentRepositoryDB;

@Service
public class PricelistItemMedicamentServiceImpl implements PricelistItemMedicamentService {
	
	@Autowired
	private PricelistItemMedicamentRepositoryDB pricelistItemRepository;

	@Autowired
	private PriceService priceService;
	@Override
	public Set<PricelistItemMedicament> findAllPharmacy(Long id) {
		Set<PricelistItemMedicament> items =  pricelistItemRepository.findAllPricelistItemMedicaments(id);
		return items;
	}

	@Override
	public Set<PricelistItemMedicament> findPharmacyForMedicament(Long id) {
		Set<PricelistItemMedicament> items =  pricelistItemRepository.findAllMeds(id);
		return items;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PricelistItemMedicament findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PricelistItemMedicament create(PricelistItemMedicament pricelistItem) throws Exception {
		return pricelistItemRepository.save(pricelistItem);
	}

	@Override
	public PricelistItemMedicament update(PricelistItemMedicament pricelistItem) throws Exception {
		PricelistItemMedicament item = pricelistItemRepository.findOnePricelistItemMedicament(pricelistItem.getId());
		if(item == null) {
			return null;
		}
		pricelistItemRepository.save(pricelistItem);
		return item;
		
	
	}


	@Override
	public PricelistItemMedicament findOnePricelistItemMedicament(Long id) {
		PricelistItemMedicament item = pricelistItemRepository.findOnePricelistItemMedicament(id);
		if(item == null) {
			return null;
		}
		return item;
	}


	@Override
	public Collection<PricelistItemMedicament> findAllMeds(Long id) {
		Collection<PricelistItemMedicament> items = pricelistItemRepository.findAllMeds(id);
		
		return items;
	}

	@Override
	public PricelistItemMedicament findOneInPharmacy(Long medicamentId, Long pharmacyId) {
		return pricelistItemRepository.findOnePricelistItemMedicamentInPharmacy(medicamentId, pharmacyId); }

  @Override
	public void checkPromotions(Set<PricelistItemMedicament> pricelistItems) throws Exception {

		for(PricelistItemMedicament p :pricelistItems) {
			for(Price pp : p.getPrice()) {
				if(pp.isPromotion() && pp.getDateTo().isEqual(LocalDate.now())) {
					pp.setDeleted(true);
					priceService.update(pp);
					priceService.findPrice(p.getPrice(), pp.getDateFrom());
					
					
				}
				
			}
		}
	}

	@Override
	public PricelistItemMedicament findByPharmacyAndMed(Long id, Long pId) {
		PricelistItemMedicament pMed = pricelistItemRepository.findByPharmacyAndMed(id, pId);
		if(pMed == null) {
			return null;
		}
		return pMed;
	}

	@Override
	public Set<PricelistItemMedicament> findAllPricelistItems() {
		Set<PricelistItemMedicament> items = pricelistItemRepository.findAllPricelistItems();
		if(items == null) {
			return null;
		}
		return items;
	}

}
