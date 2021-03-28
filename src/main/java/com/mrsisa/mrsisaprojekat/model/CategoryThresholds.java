package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CategoryThresholds {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "threshold", unique = true, nullable = false)
	private Integer threshold;
	
	@Column(name = "category", unique = true, nullable = false)
	private Category category;
	
	public CategoryThresholds() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
