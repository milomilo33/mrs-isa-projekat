package com.mrsisa.mrsisaprojekat.dto;


import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.Medicament;

public class MedicamentDTO {
	
	private Long id;
	private String name;
	private String type;
	private String form;
	private String mode;
	private String annotation;
	private String structure;
	private String manufacturer;
	private int points;
	
	public MedicamentDTO() {
		
	}
	public MedicamentDTO(Medicament m) {
		this(m.getId(), m.getName(), m.getType(), m.getMedicamentForm().name(), m.getIssuanceMode().name(), m.getAnnotations(), m.getStructure(), m.getManufacturer(), m.getPoints());
	}
	
	public MedicamentDTO(Long id, String name, String type, String medicamentForm, String issuanceMode,
			String annotations, String structure, String manufacturer, int points) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.annotation = annotations;
		this.structure = structure;	
		this.manufacturer = manufacturer;
		this.mode = issuanceMode;
		this.form = medicamentForm;
		this.points = points;
	}
	
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getForm() {
		return form;
	}
	public String getMode() {
		return mode;
	}
	public String getAnnotation() {
		return annotation;
	}
	public String getStructure() {
		return structure;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public int getPoints() {
		return points;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
}
