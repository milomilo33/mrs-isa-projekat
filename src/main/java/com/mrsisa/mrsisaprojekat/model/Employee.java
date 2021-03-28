package com.mrsisa.mrsisaprojekat.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Employee extends User {
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private WorkCalendar calendar;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Request> requests;
	
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

	
	
	

}
