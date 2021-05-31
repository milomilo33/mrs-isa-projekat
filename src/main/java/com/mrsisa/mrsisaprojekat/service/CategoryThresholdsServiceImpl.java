package com.mrsisa.mrsisaprojekat.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.repository.CategoryThresholdRepositoryDB;

@Service
public class CategoryThresholdsServiceImpl implements CategoryThresholdsService{

	@Autowired
	private CategoryThresholdRepositoryDB categoryThresholdsRepository;
	
	@Autowired
	private PatientService patientService;
	
	@Override
	public List<CategoryThresholds> findAll() {
		return categoryThresholdsRepository.findByOrderByThresholdAsc();
	}

	@Override
	public CategoryThresholds findOne(Long id) {
		return categoryThresholdsRepository.findById(id).orElse(null);
	}

	@Override
	public CategoryThresholds update(CategoryThresholds categoryThresholds) {
		CategoryThresholds ct = categoryThresholdsRepository.save(categoryThresholds);
		try {
			this.checkPatientsCategories();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;	
	}

	@Override
	public CategoryThresholds getOneWithCategory(String category) {
		
		return categoryThresholdsRepository.getOneWithCategory(category);
	}

	@Override
	public CategoryThresholds create(CategoryThresholds category) {
		CategoryThresholds ct = categoryThresholdsRepository.save(category);
		try {
			this.checkPatientsCategories();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}

	@Override
	public boolean delete(CategoryThresholds category) {
		if(category.getCategory().equalsIgnoreCase("REGULAR")) {
			return false;
		}
		System.out.println(category.getThreshold());
		category.setDeleted(true);
		categoryThresholdsRepository.save(category);
		
		try {
			this.checkPatientsCategories();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	

	@Override
	public void checkPatientCategory(Patient patient) throws Exception {
		List<CategoryThresholds> categories = this.findAll();
		CategoryThresholds category = new CategoryThresholds();
		int index = 0; 
		for(CategoryThresholds ct : categories) {
			if(patient.getLoyaltyPoints() > ct.getThreshold()) {
				index = index + 1;	
				
			}
		}
		if(index == categories.size()) {
			index = index - 1;
		}
		category = categories.get(index);
		if(!category.getCategory().equalsIgnoreCase(patient.getCategory())) {
			patient.setCategory(category.getCategory());
			patientService.update(patient);
		}
	}

	@Override
	public void checkPatientsCategories() throws Exception {
		Collection<Patient> patients = patientService.findAll();
		for(Patient p : patients) {
			this.checkPatientCategory(p);
		}
	}
}
