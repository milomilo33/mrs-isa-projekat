package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Employee;

public class EmployeeDTO {
	
	private String email;
	private String name;
	private String lastName;
	
	
	public EmployeeDTO() {
		
	}
	
	public EmployeeDTO(Employee e) {
		this(e.getEmail(), e.getName(),e.getLastName());
		
	}
	public EmployeeDTO(String email, String name, String lastName) {
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
