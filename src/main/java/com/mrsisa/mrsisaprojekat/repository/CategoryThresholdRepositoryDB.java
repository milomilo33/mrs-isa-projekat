package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;

public interface CategoryThresholdRepositoryDB extends JpaRepository<CategoryThresholds, Long>{

	@Query("select c from CategoryThresholds c where c.category=?1")
	CategoryThresholds getOneWithCategory(int category);
	
	List<CategoryThresholds> findByOrderByCategoryAsc();
}
