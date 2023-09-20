package com.mrsisa.mrsisaprojekat.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import com.mrsisa.mrsisaprojekat.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public interface PharmacyRepositoryDB extends JpaRepository<Pharmacy, Long>{
	@Query("select p from Pharmacy p join fetch p.address")
	List<Pharmacy> getAllWithAddress();
	
	@Query("select p from Pharmacy p join fetch p.address where p.id=?1")
	Pharmacy getOnePharmacy(Long id);
	
	@Query("select p.medicamentItems from Pharmacy p where p.id=?1")
	List<MedicamentItem> getAllMedicaments(Long id);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select p from Pharmacy p join fetch p.medicamentItems where p.id=?1")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Pharmacy getOneWithMedicaments(Long id);

	@Query("select p from Pharmacy p join fetch p.medicamentItems")
	Set<Pharmacy> getAllWithMedicaments();
	
	@Query("select p from Pharmacy p join fetch p.dermatologists where p.id=?1")
	Pharmacy getOneWithDermatologists(Long id);

	@Query("select p from Pharmacy p join fetch p.pharmacists where p.id=?1")
	Pharmacy getOneWithPharmacists(Long id);
	
	@Query("select p from Pharmacy p join fetch p.appointments where p.id=?1")
	Pharmacy getOneWithAppointments(Long id);
	

	@Query("select p.ratings from Pharmacy p where p.id=?1")
	List<Rating> getRatings(Long id);

	@Query("select p from Pharmacy p join fetch p.ratings pr join fetch p.dermatologists join fetch p.pharmacists join fetch p.medicamentItems where p.id = ?1")
    Pharmacy loadWithRatings(Long ratedEntityId);

	@Query("select p from Pharmacy p join fetch p.medicamentItems pm where p.id = ?1")
	Pharmacy findMedicamentItem(Long pharmacyId);

	@Query("select p from Pharmacy p left join fetch p.ratings pr join fetch p.dermatologists join fetch p.pharmacists join fetch p.medicamentItems where p.id = ?1 and pr.patient.email = ?2")
	Pharmacy loadWithRatingOfUser(Long id, String email);
	
	
	@Query("select p from Pharmacy p join fetch p.requests where p.id=?1")
	Pharmacy getOneWithRequests(Long id);
	
	@Query("select p from Pharmacy p left join fetch p.admins")
	Set<Pharmacy> getAllWithAdmins();


	@Query("select p from Pharmacy p join fetch p.ratings")
	Collection<Pharmacy> findWithRating();

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Pharmacy p join fetch p.medicamentItems where p.id=?1")
	Pharmacy getOneWithMedicamentsPatient(Long id);}
