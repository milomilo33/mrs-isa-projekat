package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;

public interface PharmacyAdminRepositoryDB extends JpaRepository<AdminPharmacy, String>{

	@Query("select a from AdminPharmacy a join fetch a.address")
	List<AdminPharmacy> getAllWithAddress();
	
	@Query("select a from AdminPharmacy a join fetch a.roles where a.email=?1")
	AdminPharmacy getOneLogin(String id);
	
	
	@Query("select a from AdminPharmacy a join fetch a.address join fetch a.pharmacy where a.email=?1")
	AdminPharmacy getOnePharmacyAdmin(String id);
	
	
	@Query("select a from AdminPharmacy a join fetch a.requestMedicaments where a.email=?1")
	AdminPharmacy getOneWithRequestMedicaments(String id);
	
	@Query("select a from AdminPharmacy a join fetch a.pharmacy where a.pharmacy.id=?1")
	Set<AdminPharmacy> getAllEmployeedInPharmacy(Long id);
}
