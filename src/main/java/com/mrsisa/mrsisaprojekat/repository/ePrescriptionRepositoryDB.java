package com.mrsisa.mrsisaprojekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.ePrescription;

public interface ePrescriptionRepositoryDB extends JpaRepository<ePrescription, Long> {

	@Modifying
	@Query("update ePrescription ep set ep.deleted = true where ep.id=?1")
	void deleteOne(Long id);
	
	@Modifying
	@Query("update ePrescription ep set ep.done = true where ep.id=?1")
	void done(Long id);
	
}
