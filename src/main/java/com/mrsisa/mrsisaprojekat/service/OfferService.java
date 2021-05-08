package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.Offer;

public interface OfferService {
	
	Offer save(Offer offer);
	
	Offer update(Offer offer);
	
	void delete(Long id);
}
