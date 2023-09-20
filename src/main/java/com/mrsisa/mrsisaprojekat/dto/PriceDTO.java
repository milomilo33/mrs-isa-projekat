package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalDate;

import com.mrsisa.mrsisaprojekat.model.Price;

public class PriceDTO {
	private Long id;
	private double value;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private int points;
	private boolean deleted;
	
	public PriceDTO() {
		
	}
	
	public PriceDTO(Price price) {
		this(price.getId(),price.getValue(),price.getDateFrom(),price.getDateTo(),price.getPoints(),price.isDeleted());
	}
	
	public PriceDTO(Long id, double value, LocalDate dateFrom, LocalDate dateTo,int points, boolean deleted) {
		super();
		this.id = id;
		this.value = value;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.points = points;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}



	public double getValue() {
		return value;
	}

	

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	

	public LocalDate getDateTo() {
		return dateTo;
	}

	
	public int getPoints() {
		return points;
	}

	

	public boolean isDeleted() {
		return deleted;
	}


	

}
