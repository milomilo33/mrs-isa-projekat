package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;
import com.mrsisa.mrsisaprojekat.repository.CategoryThresholdRepositoryDB;

@Service
public class CategoryThresholdsServiceImpl implements CategoryThresholdsService{

	@Autowired
	private CategoryThresholdRepositoryDB categoryThresholdsRepository;
	
	@Override
	public List<CategoryThresholds> findAll() {
		return categoryThresholdsRepository.findByOrderByCategoryAsc();
	}

	@Override
	public CategoryThresholds findOne(Long id) {
		return categoryThresholdsRepository.findById(id).orElse(null);
	}

	@Override
	public CategoryThresholds update(CategoryThresholds categoryThresholds) {
		return categoryThresholdsRepository.save(categoryThresholds);
	}

	@Override
	public CategoryThresholds getOneWithCategory(int category) {
		
		return categoryThresholdsRepository.getOneWithCategory(category);
	}

}
