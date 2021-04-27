package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Supplier;



public interface SupplierRepositoryDB extends JpaRepository<Supplier, String>{

	@Query("select s from Supplier s join fetch s.roles where s.email=?1")
	Supplier getOneLogin(String id);
	
	@Query("select s from Supplier s join fetch s.address where s.email=?1")
	Supplier getOneWithAddress(String email);

	@Query("select s from Supplier s join fetch s.address where s.deleted = false")
	List<Supplier> getAllWithAddress();
}
