package com.mrsisa.mrsisaprojekat.service;

import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.Offer;
import com.mrsisa.mrsisaprojekat.model.OfferStatus;

public interface OfferService {
	
	Offer save(Offer offer);
	
	Offer update(Offer offer);
	
	void delete(Long id);
	
	Set<Offer> supplierOffers(String email);
	
	Set<Offer> offersForOrder(Long id);
	
	Offer findOffer(Long id);

	Set<Offer> filterOffer(String email, OfferStatus status);
}
