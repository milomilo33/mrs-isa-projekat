package com.mrsisa.mrsisaprojekat.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Offer;

public interface OfferRepositoryDB extends JpaRepository<Offer, Long> {
	
	@Query("select o from Offer o join fetch o.order where o.order.id=?1")
	Set<Offer> getOffersForOrder(Long id);
	
	@Query("select o from Offer o join fetch o.supplier join fetch o.order where o.supplier.email=?1")
	Set<Offer> getOffersForSupplier(String email);

}
