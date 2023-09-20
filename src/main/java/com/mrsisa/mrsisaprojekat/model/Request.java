package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Request {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@Column(name = "dateFrom", unique = false, nullable = false)
	private LocalDate dateFrom;
	
	@Column(name = "dateTo", unique = false, nullable = false)
	private LocalDate dateTo;
	
	@Column(name = "accepted", unique = false, nullable = false)
	private boolean accepted;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;
	
	@Column(name = "rejectionReason", unique = false, nullable = true)
	private String rejectionReason;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Employee employee;
	
	@Version
	@Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	public Request() {}
	
	public Request(Long id, String description, LocalDate dateFrom, LocalDate dateTo, boolean accepted,
			boolean deleted) {
		super();
		this.id = id;
		this.description = description;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.accepted = accepted;
		this.deleted = deleted;
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

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	

	
	

}
