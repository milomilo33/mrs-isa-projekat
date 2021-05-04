package com.mrsisa.mrsisaprojekat.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItem;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;
import com.mrsisa.mrsisaprojekat.repository.PriceRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PricelistItemRepositoryDB;

@Service
public class PricelistItemMedicamentServiceImpl implements PricelistItemMedicamentService {
	
	@Autowired
	private PricelistItemRepositoryDB pricelistItemRepository;

	@Autowired
	private PriceRepositoryDB priceRepository;
	@Override
	public Collection<PricelistItemMedicament> findAllPharmacy(Long id) {
		ArrayList<PricelistItemMedicament> items = (ArrayList<PricelistItemMedicament>) pricelistItemRepository.findAllPricelistItemMedicaments(id);
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
		Price p = priceRepository.findOnePrice(pricelistItem.getPrice().getId());
		if(p == null) {
			return null;
		}
		priceRepository.save(pricelistItem.getPrice());
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

}
