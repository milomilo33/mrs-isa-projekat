package com.mrsisa.mrsisaprojekat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Medicament;

public interface MedicamentRepositoryDB extends JpaRepository<Medicament, Long>{
	@Query("select p from Medicament p join fetch p.ratings where p.id=?1 and p.deleted=false")
	Medicament getRatings(Long id);

	@Query("select m from Medicament m where lower(m.name) like lower(concat('%', concat(?1, '%')))")
	Collection<Medicament> findAllWithName(String query);

	@Query("select m from Medicament m where m.issuanceMode=?1 or m.medicamentForm=?2")
	Collection<Medicament> findFilter(int mode, int form);
}
