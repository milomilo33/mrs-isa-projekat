package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;

public interface PharmacyAdminRepositoryDB extends JpaRepository<AdminPharmacy, String>{

	@Query("select a from AdminPharmacy a join fetch a.address")
	List<AdminPharmacy> getAllWithAddress();
}
