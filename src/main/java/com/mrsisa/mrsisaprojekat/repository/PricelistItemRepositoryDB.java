package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.PricelistItem;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;


public interface PricelistItemRepositoryDB extends JpaRepository<PricelistItem, Long>{
	
	@Query("select p from PricelistItem p join fetch p.pharmacy join fetch p.price join fetch p.medicament where p.pharmacy.id=?1")
	List<PricelistItemMedicament> findAllPricelistItemMedicaments(Long id);
	
	@Query("select p from PricelistItem p join fetch p.pharmacy join fetch p.price join fetch p.medicament where p.id=?1")
	PricelistItemMedicament findOnePricelistItemMedicament(Long id);
	
	@Query("select p from PricelistItem p join fetch p.pharmacy join fetch p.price join fetch p.medicament where p.medicament.id=?1")
	List<PricelistItemMedicament> findAllMeds(Long id);

}
