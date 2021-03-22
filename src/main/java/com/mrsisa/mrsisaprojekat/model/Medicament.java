package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Medicament {
	private Long id;
	private String name,type,structure,manufacturer,annotations;
	private MedicamentForm medicamentForm;
	private IssuanceMode issuanceMode;
	private HashMap<Long,Medicament> substituteMedicaments;
	private boolean deleted;
	
	public Medicament() {}

	public Medicament(Long id, String name, String type, String structure, String manufacturer, String annotations,
			MedicamentForm medicamentForm, IssuanceMode issuanceMode, HashMap<Long, Medicament> substituteMedicaments,
			boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.structure = structure;
		this.manufacturer = manufacturer;
		this.annotations = annotations;
		this.medicamentForm = medicamentForm;
		this.issuanceMode = issuanceMode;
		this.substituteMedicaments = substituteMedicaments;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAnnotations() {
		return annotations;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public MedicamentForm getMedicamentForm() {
		return medicamentForm;
	}

	public void setMedicamentForm(MedicamentForm medicamentForm) {
		this.medicamentForm = medicamentForm;
	}

	public IssuanceMode getIssuanceMode() {
		return issuanceMode;
	}

	public void setIssuanceMode(IssuanceMode issuanceMode) {
		this.issuanceMode = issuanceMode;
	}

	public HashMap<Long, Medicament> getSubstituteMedicaments() {
		return substituteMedicaments;
	}

	public void setSubstituteMedicaments(HashMap<Long, Medicament> substituteMedicaments) {
		this.substituteMedicaments = substituteMedicaments;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	
	
	

}
