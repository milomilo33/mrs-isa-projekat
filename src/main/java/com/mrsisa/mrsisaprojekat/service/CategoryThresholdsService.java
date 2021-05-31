package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;
import com.mrsisa.mrsisaprojekat.model.Patient;

public interface CategoryThresholdsService {

	List<CategoryThresholds> findAll();
	
	CategoryThresholds findOne(Long id);
	
	CategoryThresholds update(CategoryThresholds categoryThresholds);
	
	CategoryThresholds getOneWithCategory(String category);
	
	CategoryThresholds create(CategoryThresholds category);
	
	boolean delete(CategoryThresholds category) throws Exception;
	
	void checkPatientCategory(Patient patient) throws Exception;
	
	void checkPatientsCategories() throws Exception;
	
}
