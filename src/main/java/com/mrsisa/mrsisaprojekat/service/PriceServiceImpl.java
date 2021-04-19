package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.repository.PriceRepositoryDB;

@Service
public class PriceServiceImpl implements PriceService{
	
	@Autowired
	PriceRepositoryDB priceRepository;

	@Override
	public Collection<Price> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Price findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Price create(Price price) throws Exception {
		if(price.getId() != null) {
			return null;
		}
		Price savedPrice = priceRepository.save(price);
		return savedPrice;
	}

	@Override
	public Price update(Price price) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(Long id) {
		// TODO Auto-generated method stub
		
	}

}
