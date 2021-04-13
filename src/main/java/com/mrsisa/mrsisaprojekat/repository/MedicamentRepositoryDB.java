package com.mrsisa.mrsisaprojekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Medicament;

public interface MedicamentRepositoryDB extends JpaRepository<Medicament, Long>{
	@Query("select p from Medicament p join fetch p.ratings where p.id=?1 and p.deleted=false")
	Medicament getRatings(Long id);
}
