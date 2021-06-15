package com.mrsisa.mrsisaprojekat.repository;

import java.util.Set;

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
	
	
	@Query("select e from ePrescription e join fetch e.pharmacy join fetch e.prescriptionMedicaments where e.pharmacy.id=?1")
	Set<ePrescription> findPharmacyePrescriptions(Long id);

	@Query("select ep from ePrescription ep where ep.patient.email = ?1")
	Set<ePrescription> findByPatient(String patientEmail);

	@Query("select ep from ePrescription ep join fetch ep.prescriptionMedicaments eppm join fetch ep.pharmacy where eppm.id = ?1")
	ePrescription findePrescriptionWherePrescriptionMedicament(Long id);
}
