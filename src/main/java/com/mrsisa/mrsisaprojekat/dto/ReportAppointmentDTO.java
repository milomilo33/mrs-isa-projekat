package com.mrsisa.mrsisaprojekat.dto;

import java.util.ArrayList;
import java.util.HashSet;

public class ReportAppointmentDTO {
	
	public ArrayList<MonthAppointmentDTO> monthAppoinntments;

	
	public ReportAppointmentDTO() {
		
	}
	public ReportAppointmentDTO(ArrayList<MonthAppointmentDTO> monthAppoinntments) {
		super();
		this.monthAppoinntments = monthAppoinntments;
	}
	public ArrayList<MonthAppointmentDTO> getMonthAppoinntments() {
		return monthAppoinntments;
	}
	public void setMonthAppoinntments(ArrayList<MonthAppointmentDTO> monthAppoinntments) {
		this.monthAppoinntments = monthAppoinntments;
	}
	
	
	
	

}
