package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;
import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public interface PharmacyRepositoryDB extends JpaRepository<Pharmacy, Long>{
	@Query("select p from Pharmacy p join fetch p.address")
	List<Pharmacy> getAllWithAddress();
	
	@Query("select p from Pharmacy p join fetch p.address where p.id=?1")
	Pharmacy getOnePharmacy(Long id);
	
	@Query("select p.medicamentItems from Pharmacy p where p.id=?1")
	List<MedicamentItem> getAllMedicaments(Long id);
	
	@Query("select p from Pharmacy p join fetch p.medicamentItems where p.id=?1")
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

	@Query("select p from Pharmacy p join fetch p.ratings where p.id = ?1")
    Pharmacy loadWithRatings(Long ratedEntityId);
	@Query("select p from Pharmacy p join fetch p.medicamentItems pm where p.id = ?1")
	Pharmacy findMedicamentItem(Long pharmacyId);

	@Query("select p from Pharmacy p left join fetch p.ratings pr where p.id = ?1 and pr.patient.email = ?2")
	Pharmacy loadWithRatingOfUser(Long id, String email);
	
	@Query("select p from Pharmacy p join fetch p.requests where p.id=?1")
	Pharmacy getOneWithRequests(Long id);
}
