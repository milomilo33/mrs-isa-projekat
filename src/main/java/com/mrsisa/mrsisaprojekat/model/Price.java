package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Price {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "value", unique = false, nullable = false)
	private double value;
	
	@Column(name = "dateFrom", unique = false, nullable = false)
	private LocalDate dateFrom;
	
	@Column(name = "dateTo", unique = false, nullable = true)
	private LocalDate dateTo;
	
	@Column(name = "points", unique = false, nullable = false)
	private int points;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	public Price() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	

}
