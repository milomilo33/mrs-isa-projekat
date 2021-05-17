package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.AppointmentPoints;

public class AppointmentPointsDTO {

	private Long id;
	private String type;
	private int points;
	
	public AppointmentPointsDTO() {
		
	}
	
	public AppointmentPointsDTO(AppointmentPoints points) {
		this.id = points.getId();
		this.type = points.getType().name();
		this.points = points.getPoints();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
