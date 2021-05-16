package com.mrsisa.mrsisaprojekat.dto;

import java.time.Month;
import java.util.HashMap;

public class MonthAppointmentDTO {
	
	public enum Quarter {Q1,Q2,Q3,Q4};
	HashMap<Integer,Integer> values;
	public Month month;
	public int day;
	public int value;
	public Quarter quarter;
	
	
	public MonthAppointmentDTO() {
		
	}

	public MonthAppointmentDTO(HashMap<Integer, Integer> values, Month month) {
		super();
		this.values = values;
		this.month = month;
	}

	public HashMap<Integer, Integer> getValues() {
		return values;
	}

	public void setValues(HashMap<Integer, Integer> values) {
		this.values = values;
	}

	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Quarter getQuarter() {
		return quarter;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	
	
	
	
	
	
	
	
	
	

}
