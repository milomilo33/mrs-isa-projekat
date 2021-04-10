package com.mrsisa.mrsisaprojekat.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Dermatologist;

public interface DermatologistRepositoryDB extends JpaRepository<Dermatologist, String>{

	@Query("select d from Dermatologist d join fetch d.address where d.deleted = false")
	List<Dermatologist> getAllWithAddress();
	
	@Query("select d from Dermatologist d join fetch d.address where d.email=?1")
	Dermatologist getOneDermatologist(String id);
		
	@Modifying
	@Query("update Dermatologist d set d.deleted = true where d.email=?1")
	void deleteOne(String email);
}
