package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicalReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@Column(name = "date", unique = false, nullable = false)
	private LocalDateTime date;
	
	@OneToOne(fetch = FetchType.LAZY)
	private ePrescription eprescription;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	public MedicalReport() {}
	
	public MedicalReport(Long id, String description, LocalDateTime date, ePrescription eprescription,
			boolean deleted) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
		this.eprescription = eprescription;
		this.deleted = deleted;
	}

	public MedicalReport(String description, LocalDateTime date, ePrescription eprescription) {
		super();
		this.description = description;
		this.date = date;
		this.eprescription = eprescription;
		this.deleted = false;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public ePrescription getEprescription() {
		return eprescription;
	}

	public void setEprescription(ePrescription eprescription) {
		this.eprescription = eprescription;
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	

	

}
