package com.mrsisa.mrsisaprojekat.model;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Employee extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private WorkCalendar calendar;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Request> requests;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	private Set<WorkHour> workHour;

	@Column(name = "currentlyInAppointment", nullable = false, columnDefinition = "boolean default false")
	private boolean currentlyInAppointment;

	public boolean isCurrentlyInAppointment() {
		return currentlyInAppointment;
	}

	public void setCurrentlyInAppointment(boolean currentlyInAppointment) {
		this.currentlyInAppointment = currentlyInAppointment;
	}

	public Employee() {}

	
	public WorkCalendar getCalendar() {
		return calendar;
	}


	public void setCalendar(WorkCalendar calendar) {
		this.calendar = calendar;
	}


	

	public Set<Rating> getRatings() {
		return ratings;
	}


	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}


	public Set<Request> getRequests() {
		return requests;
	}

	public void setRequests(Set<Request> requests) {
		this.requests = requests;
	}


	public Set<WorkHour> getWorkHour() {
		return workHour;
	}


	public void setWorkHour(Set<WorkHour> workHour) {
		this.workHour = workHour;
	}

	
	
	

}
