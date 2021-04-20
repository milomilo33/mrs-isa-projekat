package com.mrsisa.mrsisaprojekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Price;


public interface PriceRepositoryDB  extends JpaRepository<Price, Long> {
	
	@Query("select p from Price p where p.id=?1")
	Price findOnePrice(Long id);

}
