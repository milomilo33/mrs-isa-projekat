package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Supplier;



public interface SupplierRepositoryDB extends JpaRepository<Supplier, String>{

	@Query("select s from Supplier s join fetch s.roles where s.email=?1 and s.deleted = false")
	Supplier getOneLogin(String id);
	
	@Query("select s from Supplier s join fetch s.address where s.email=?1 and s.deleted = false")
	Supplier getOneWithAddress(String email);
	
	@Modifying
	@Query("update Supplier s set s.deleted = true where s.email=?1")
	void deleteOne(String email);

	@Query("select s from Supplier s join fetch s.address where s.deleted = false")
	List<Supplier> getAllWithAddress();
}
