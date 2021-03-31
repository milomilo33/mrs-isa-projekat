package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Pharmacist;

public interface PharmacistRepositoryDB extends JpaRepository<Pharmacist, String> {
	
	@Query("select p from Pharmacist p join fetch p.address")
	List<Pharmacist> getAllWithAddress();
	
	
	
	@Query("select p from Pharmacist p join fetch p.address where p.email=?1")
	Pharmacist getOnePharmacist(String id);

}
