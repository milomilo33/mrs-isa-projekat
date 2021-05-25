package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;

public interface CategoryThresholdsService {

	List<CategoryThresholds> findAll();
	
	CategoryThresholds findOne(Long id);
	
	CategoryThresholds update(CategoryThresholds categoryThresholds);
	
	CategoryThresholds getOneWithCategory(int category);
	
}
