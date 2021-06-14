package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;


public interface PricelistItemMedicamentRepositoryDB extends JpaRepository<PricelistItemMedicament, Long>{
	
	@Query("select p from PricelistItemMedicament p left join fetch p.pharmacy left join fetch p.price join fetch p.medicament where p.pharmacy.id=?1")
	Set<PricelistItemMedicament> findAllPricelistItemMedicaments(Long id);
	
	@Query("select p from PricelistItemMedicament p join fetch p.pharmacy join fetch p.price join fetch p.medicament where p.id=?1")
	PricelistItemMedicament findOnePricelistItemMedicament(Long id);
	
	@Query("select p from PricelistItemMedicament p join fetch p.pharmacy join fetch p.price join fetch p.medicament where p.pharmacy.id=?1")
	Set<PricelistItemMedicament> findAllMeds(Long id);
	
	@Query("select p from PricelistItemMedicament p join fetch p.pharmacy join fetch p.price join fetch p.medicament where p.medicament.id=?1 and p.pharmacy.id=?2")
	PricelistItemMedicament findOnePricelistItemMedicamentInPharmacy(Long medicamentId, Long pharmacyId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from PricelistItemMedicament p join fetch p.pharmacy join fetch p.price join fetch p.medicament where p.medicament.id=?1 and p.pharmacy.id=?2")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	PricelistItemMedicament findByPharmacyAndMed(Long id, Long pId);
	
	@Query("select p from PricelistItemMedicament p left join fetch p.pharmacy left join fetch p.price join fetch p.medicament")
	Set<PricelistItemMedicament> findAllPricelistItems();

}
