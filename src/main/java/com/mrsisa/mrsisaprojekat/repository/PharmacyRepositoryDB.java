package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public interface PharmacyRepositoryDB extends JpaRepository<Pharmacy, Long>{
	@Query("select p from Pharmacy p join fetch p.address")
	List<Pharmacy> getAllWithAddress();
	
	@Query("select p from Pharmacy p join fetch p.address where p.id=?1")
	Pharmacy getOnePharmacy(Long id);
	
	@Query("select p.medicamentItems from Pharmacy p where p.id=?1")
	List<MedicamentItem> getAllMedicaments(Long id);

}
