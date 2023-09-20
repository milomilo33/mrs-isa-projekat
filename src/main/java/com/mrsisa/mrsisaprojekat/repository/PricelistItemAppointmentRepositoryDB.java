package com.mrsisa.mrsisaprojekat.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;

public interface PricelistItemAppointmentRepositoryDB extends JpaRepository<PricelistItemAppointment, Long>{
	
	@Query("select p from PricelistItemAppointment p left join fetch p.pharmacy left join fetch p.price where p.pharmacy.id=?1")
	Set<PricelistItemAppointment> findAllPricelistItemAppointments(Long id);
	
	@Query("select p from PricelistItemAppointment p join fetch p.pharmacy join fetch p.price where p.id=?1")
	PricelistItemAppointment findOnePricelistItemAppointments(Long id);

}