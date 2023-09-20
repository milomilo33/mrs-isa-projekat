package com.mrsisa.mrsisaprojekat.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

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

	void findPrice(Set<Price> prices,LocalDate date);
}
