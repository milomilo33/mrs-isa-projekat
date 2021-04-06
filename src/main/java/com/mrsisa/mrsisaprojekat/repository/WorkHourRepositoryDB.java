package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.WorkHour;

public interface WorkHourRepositoryDB  extends JpaRepository<WorkHour, Long>{
	
	@Query("select w from WorkHour w join fetch w.pharmacy")
	List<WorkHour> getAllWithPharmacy();
	
	@Query("select w from WorkHour w join fetch w.pharmacy where w.id=?1")
	WorkHour getOneWorkHour(Long id);

}
