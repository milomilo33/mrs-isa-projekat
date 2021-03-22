package com.mrsisa.mrsisaprojekat.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Employee extends User {
	
	private WorkCalendar calendar;
	private ArrayList<Evaluation> evaluations;
	private HashMap<Long,Request> requests;
	
	public Employee() {}

	
	public WorkCalendar getCalendar() {
		return calendar;
	}


	public void setCalendar(WorkCalendar calendar) {
		this.calendar = calendar;
	}


	

	public ArrayList<Evaluation> getEvaluations() {
		return evaluations;
	}


	public void setEvaluations(ArrayList<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}


	public HashMap<Long, Request> getRequests() {
		return requests;
	}

	public void setRequests(HashMap<Long, Request> requests) {
		this.requests = requests;
	}

	
	
	

}
