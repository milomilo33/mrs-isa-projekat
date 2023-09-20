package com.mrsisa.mrsisaprojekat.repository;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import org.springframework.data.jpa.repository.*;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Set;

public interface PharmacyAdminRepositoryDB extends JpaRepository<AdminPharmacy, String>{
	@Query("select a from AdminPharmacy a join fetch a.address where a.deleted = false")
	List<AdminPharmacy> getAllWithAddress();
	
	
	@Query("select a from AdminPharmacy a join fetch a.roles where a.email=?1 and a.deleted = false")
	AdminPharmacy getOneLogin(String id);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select a from AdminPharmacy a where a.email=?1 and a.deleted = false")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	AdminPharmacy getOneForConcurrent(String id);
	
	@Query("select a from AdminPharmacy a join fetch a.address join fetch a.pharmacy where a.email=?1 and a.deleted = false")
	AdminPharmacy getOnePharmacyAdmin(String id);
	
	@Modifying
	@Query("update AdminPharmacy a set a.deleted = true where a.email=?1")
	void deleteOne(String email);
	
	@Query("select a from AdminPharmacy a join fetch a.requestMedicaments where a.email=?1 and a.deleted = false")
	AdminPharmacy getOneWithRequestMedicaments(String id);
	
	@Query("select a from AdminPharmacy a join fetch a.pharmacy where a.pharmacy.id=?1")
	Set<AdminPharmacy> getAllEmployeedInPharmacy(Long id);
	
//	@Query("select a from AdminPharmacy a left join fetch a.pharmacy where a.pharmacy is null")
	Set<AdminPharmacy> findByPharmacyIsNull();
}
