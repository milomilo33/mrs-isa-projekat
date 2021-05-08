package com.mrsisa.mrsisaprojekat.service;

import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.Offer;

public interface OfferService {
	
	Offer save(Offer offer);
	
	Offer update(Offer offer);
	
	void delete(Long id);
	
	Set<Offer> offersForOrder(Long id);
}
