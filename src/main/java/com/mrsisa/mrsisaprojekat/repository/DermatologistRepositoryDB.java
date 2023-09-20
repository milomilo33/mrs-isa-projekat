package com.mrsisa.mrsisaprojekat.repository;


import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface DermatologistRepositoryDB extends JpaRepository<Dermatologist, String>{

	@Query("select d from Dermatologist d join fetch d.address where d.deleted = false")
	List<Dermatologist> getAllWithAddress();
	
	@Query("select d from Dermatologist d join fetch d.address join fetch d.medicalExaminations join fetch d.pharmacies where d.email=?1")
	Dermatologist getOneDermatologist(String id);
		
	@Modifying
	@Query("update Dermatologist d set d.deleted = true where d.email=?1")
	void deleteOne(String email);
	
	@Query("select d from Dermatologist d join fetch d.roles where d.email=?1")
	Dermatologist getOneLogin(String id);

	@Query("select d from Dermatologist d join fetch d.medicalExaminations where d.email = ?1")
	Dermatologist getExaminations(String email);
	
	
	@Query("select d from Dermatologist d join fetch d.ratings where d.email=?1 and d.deleted=false")
	Dermatologist getRatings(String email);

	@Query("select d from Dermatologist d left join fetch d.ratings dr where d.email = ?1 and dr.patient.email = ?2")
	Dermatologist loadWithRatingOfUser(String dermatologistEmail, String patientEmail);

	@Query("select d from Dermatologist d join fetch d.address where d.email=?1 and d.deleted=false")
	Dermatologist getOneWithAddress(String email);

	@Query("select d from Dermatologist d join fetch d.address join fetch d.medicalExaminations where d.email = ?1")
	Dermatologist getDermatologistWithExaminations(String email);
	@Transactional
	@Modifying
	@Query(value="delete from  dermatologists_pharmacies d where d.dermatologist_id=?1  and d.pharmacy_id=?2", nativeQuery = true)
	void removeDermatologistFromPharmacy(String email, Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from Dermatologist d where d.email = :email")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Dermatologist findOneWithLock(@Param("email") String email);
}
