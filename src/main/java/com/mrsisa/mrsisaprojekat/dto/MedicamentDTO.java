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
	
	public MedicamentDTO() {
		
	}
	public MedicamentDTO(Medicament m) {
		this(m.getId(), m.getName(), m.getType(), m.getMedicamentForm().name(), m.getIssuanceMode().name(), m.getAnnotations(), m.getStructure(), m.getManufacturer());
	}
	
	public MedicamentDTO(Long id, String name, String type, String medicamentForm, String issuanceMode,
			String annotations, String structure, String manufacturer) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.annotation = annotations;
		this.structure = structure;	
		this.manufacturer = manufacturer;
		this.mode = issuanceMode;
		this.form = medicamentForm;
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
	
	
}
