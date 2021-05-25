package com.mrsisa.mrsisaprojekat.dto;

public class QRItemDTO {

	private Long medicamentId;
	private int quantity;
	private int points;
	private String name;
	
	public QRItemDTO() {}
	
	public QRItemDTO(Long medicamentId, int quantity) {
		this.medicamentId = medicamentId;
		this.quantity = quantity;
	}

	public QRItemDTO(Long medicamentId, int quantity, int points, String name) {
		this.medicamentId = medicamentId;
		this.quantity = quantity;
		this.points = points;
		this.name = name;
		
	}
	public Long getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Long medicamentId) {
		this.medicamentId = medicamentId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
