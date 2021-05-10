package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;

public interface PharmacistRepositoryDB extends JpaRepository<Pharmacist, String> {
	
	@Query("select p from Pharmacist p join fetch p.address where p.deleted = false")
	List<Pharmacist> getAllWithAddress();
	
	@Query("select p from Pharmacist p join fetch p.address join fetch p.counselings where p.email=?1")
	Pharmacist getOnePharmacist(String id);
	
	@Modifying
	@Query("update Pharmacist p set p.deleted = true where p.email=?1")
	void deleteOne(String email);
	
	@Query("select p from Pharmacist p join fetch p.roles where p.email=?1")
	Pharmacist getOneLogin(String id);

	@Query("select p from Pharmacist p join fetch p.address join fetch p.counselings where p.email = ?1")
	Pharmacist getPharmacistWithCounselings(String email);
	
	@Query("select p from Pharmacist p left join fetch p.ratings where p.email=?1 and p.deleted=false")
	Pharmacist getRatings(String email);

	@Query("select p from Pharmacist p left join fetch p.ratings pr where p.email = ?1 and pr.patient.email = ?2")
	Pharmacist loadWithRatingOfUser(String pharmacistEmail, String patientEmail);
}
