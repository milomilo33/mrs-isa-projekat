package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;

public interface CategoryThresholdsService {

	Collection<CategoryThresholds> findAll();
	
	CategoryThresholds findOne(Long id);
	
	CategoryThresholds update(CategoryThresholds categoryThresholds);
	
	
}
