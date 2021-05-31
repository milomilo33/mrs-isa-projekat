package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;

public interface PharmacyAdminRepositoryDB extends JpaRepository<AdminPharmacy, String>{

	@Query("select a from AdminPharmacy a join fetch a.address where a.deleted = false")
	List<AdminPharmacy> getAllWithAddress();
	
	@Query("select a from AdminPharmacy a join fetch a.roles where a.email=?1 and a.deleted = false")
	AdminPharmacy getOneLogin(String id);
	
	@Query("select a from AdminPharmacy a join fetch a.address join fetch a.pharmacy where a.email=?1 and a.deleted = false")
	AdminPharmacy getOnePharmacyAdmin(String id);
	
	@Modifying
	@Query("update AdminPharmacy a set a.deleted = true where a.email=?1")
	void deleteOne(String email);
	
	@Query("select a from AdminPharmacy a join fetch a.requestMedicaments where a.email=?1 and a.deleted = false")
	AdminPharmacy getOneWithRequestMedicaments(String id);
	
	@Query("select a from AdminPharmacy a join fetch a.pharmacy where a.pharmacy.id=?1")
	Set<AdminPharmacy> getAllEmployeedInPharmacy(Long id);
}
