package com.mrsisa.mrsisaprojekat.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.dto.PricelistItemMedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;
import com.mrsisa.mrsisaprojekat.repository.MedicamentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PriceRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PricelistItemMedicamentRepositoryDB;

@Service
public class PricelistItemMedicamentServiceImpl implements PricelistItemMedicamentService {
	
	@Autowired
	private PricelistItemMedicamentRepositoryDB pricelistItemRepository;
	
	@Autowired
	private MedicamentRepositoryDB medicamentRepository;
	
	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;

	@Autowired
	private PriceRepositoryDB priceRepository;
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
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

	@Override
	@Transactional(readOnly = false)
	public PricelistItemMedicament makePromotion(Long id, Long pId, PricelistItemMedicamentDTO pricelistItem) {
		PricelistItemMedicament pricelistUpdate = this.findByPharmacyAndMed(id,pId);
		if (pricelistUpdate == null) {
			return null;
		}
		
		for(Price p : pricelistUpdate.getPrice()) {
			if(!p.isDeleted()) {
				p.setDeleted(true);
				p.setDateTo(LocalDate.now());
				try {
					priceService.update(p);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}
		}
		Price p = new Price();
		p.setValue(pricelistItem.getPrice().get(0).getValue());
		p.setDateFrom(pricelistItem.getPrice().get(0).getDateFrom());
		p.setDateTo(pricelistItem.getPrice().get(0).getDateTo());
		p.setDeleted(false);
		p.setPoints(0);
		p.setPromotion(true);
		Price saved;
		try {
			saved = priceService.create(p);
			Set<Price> pr = pricelistUpdate.getPrice();
			pr.add(saved);
			pricelistUpdate.setPrice(pr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		try {
			pricelistUpdate = this.update(pricelistUpdate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return pricelistUpdate;
	}

	@Override
	@Transactional
	public PricelistItemMedicament savePricelistItemMedicament(PricelistItemMedicamentDTO pricelistItem) {
		if(pricelistItem.getPrice().get(0).getValue() <= 0) {
			return null;
		}
		Price price = new Price();
		price.setDeleted(false);
		price.setValue(pricelistItem.getPrice().get(0).getValue());
		price.setDateFrom(LocalDate.now());
		price.setDateTo(null);
		price.setPoints(pricelistItem.getPrice().get(0).getPoints());
		
		Price saved = priceRepository.save(price);
		
		Medicament medicament = medicamentRepository.findById(pricelistItem.getMedicament().getId()).orElse(null);
		if(medicament == null) {
			return null;
		}
		//Medicament medicament = this.medicamentService.findOne(pricelistItem.getMedicament().getId());
		//Pharmacy pharmacy = this.pharmacyService.findOne(pricelistItem.getPharmacy().getId());
		Pharmacy pharmacy = pharmacyRepository.getOnePharmacy(pricelistItem.getPharmacy().getId());
		if(pharmacy == null) {
			return null;
		}
		PricelistItemMedicament p = new PricelistItemMedicament();
		p.setMedicament(medicament);
		Set<Price> pp = new HashSet<>();
		pp.add(saved);
		p.setPrice(pp);
		p.setPharmacy(pharmacy);
		
		PricelistItemMedicament savedP = pricelistItemRepository.save(p);
		
		return savedP;
		
	}

}
