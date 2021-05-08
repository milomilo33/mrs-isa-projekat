package com.mrsisa.mrsisaprojekat.service;

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

}
