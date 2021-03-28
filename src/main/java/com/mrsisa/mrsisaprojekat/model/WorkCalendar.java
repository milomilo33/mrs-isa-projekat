package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class WorkCalendar {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Appointment> appointments;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Vacation> vacations;
	
	public WorkCalendar() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Set<Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}


	public Set<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(Set<Vacation> vacations) {
		this.vacations = vacations;
	}

	
	

}
