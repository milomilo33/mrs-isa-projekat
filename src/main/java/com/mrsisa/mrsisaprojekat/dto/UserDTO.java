package com.mrsisa.mrsisaprojekat.dto;

public class UserDTO {

	private String name;
	private String lastName;
	private String email;
	private String role;
	private String number;
	
	public UserDTO() {}
	
	public UserDTO(String name, String lastName, String email, String role, String number) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.number = number;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
