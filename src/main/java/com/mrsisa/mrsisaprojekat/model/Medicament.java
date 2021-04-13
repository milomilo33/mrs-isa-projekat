package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Medicament {
	
	@Id
	@Column(name="id", unique = true, nullable=false)
	private Long id;
	
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	@Column(name = "type", unique = false, nullable = false)
	private String type;
	
	@Column(name = "structure", unique = false, nullable = false)
	private String structure;
	
	@Column(name = "manufacturer", unique = false, nullable = false)
	private String manufacturer;
	
	@Column(name = "annotations", unique = false, nullable = true)
	private String annotations;
	
	@Column(name = "medicamentForm", unique = false, nullable = false)
	private MedicamentForm medicamentForm;
	
	@Column(name = "issuanceMode", unique = false, nullable = false)
	private IssuanceMode issuanceMode;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Medicament> substituteMedicaments;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings;
	
	public Medicament() {}

	public Medicament(Long id, String name, String type, String structure, String manufacturer, String annotations,
			MedicamentForm medicamentForm, IssuanceMode issuanceMode, Set<Medicament> substituteMedicaments,
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

	public Set<Medicament> getSubstituteMedicaments() {
		return substituteMedicaments;
	}

	public void setSubstituteMedicaments(Set<Medicament> substituteMedicaments) {
		this.substituteMedicaments = substituteMedicaments;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	
	
	

}
