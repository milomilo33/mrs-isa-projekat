package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;

public class CategoryDTO {

	private String category;
	private int threshold;
	private int discount;
	private Long id;
	
	public CategoryDTO() {
		
	}
	
	public CategoryDTO(CategoryThresholds category) {
		this.category = category.getCategory().name();
		this.threshold = category.getThreshold();
		this.id = category.getId();
		this.discount = category.getDiscount();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	
	
}
