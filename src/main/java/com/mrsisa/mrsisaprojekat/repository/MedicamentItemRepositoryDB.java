package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
public interface MedicamentItemRepositoryDB extends JpaRepository<MedicamentItem, Long> {

	@Query("select m from MedicamentItem m join fetch m.medicament where m.id=?1 and m.deleted=false")
	MedicamentItem findMedicamentItem(Long id);
	
	
	@Modifying
	@Query("update MedicamentItem m set m.deleted = true where m.id=?1")
	void deleteOne(Long id);
	
	@Modifying
	@Query("update MedicamentItem m set m.deleted = false where m.id=?1")
	void restoreOne(Long id);
	
	@Query("select m from MedicamentItem m join fetch m.medicament")
	List<MedicamentItem> findAllMedicamentItems();


}
