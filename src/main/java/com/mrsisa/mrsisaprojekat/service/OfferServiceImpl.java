package com.mrsisa.mrsisaprojekat.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Offer;
import com.mrsisa.mrsisaprojekat.repository.OfferRepositoryDB;

@Service
public class OfferServiceImpl implements OfferService{

	@Autowired 
	private OfferRepositoryDB offerRepository;
	
	
	@Override
	public Offer save(Offer offer) {
		return offerRepository.save(offer);
	}

	@Override
	public Offer update(Offer offer) {
		return offerRepository.save(offer);
	}

	@Override
	public void delete(Long id) {
		offerRepository.deleteById(id);;
	}

	@Override
	public Set<Offer> offersForOrder(Long id) {
		Set<Offer> offers = offerRepository.getOffersForOrder(id);
		if(offers == null) {
			return null;
		}
		return offers;
	}

	@Override
	public Set<Offer> supplierOffers(String email) {
		return offerRepository.getOffersForSupplier(email);

	public Offer findOffer(Long id) {
		Offer offer = offerRepository.findOffer(id);
		if(offer == null) {
			return null;
		}
		return offer;

	}

}