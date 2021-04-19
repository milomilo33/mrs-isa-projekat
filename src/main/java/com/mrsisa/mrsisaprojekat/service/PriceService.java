package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Price;

@Service
public interface PriceService {
	Collection<Price> findAll();
	
	Price findOne(Long id);
	
	Price create(Price price) throws Exception;
	
	Price update(Price price) throws Exception;
	
	void delete(Long id);
	
	void restore(Long id);

}
