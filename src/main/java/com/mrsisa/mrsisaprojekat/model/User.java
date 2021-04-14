package com.mrsisa.mrsisaprojekat.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;


@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class User {
	
	@Id
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	@Column(name="password", unique=false, nullable=false)
	private String password;
	
	@Column(name="name", unique=false, nullable=false)
	private String name;
	
	@Column(name="lastName", unique=false, nullable=false)
	private String lastName;
	
	@Column(name="phoneNumber", unique=false, nullable=false)
	private String phoneNumber;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	private Address address;
	
	@Column(name="deleted", unique=false, nullable=false)
	private boolean deleted;
	
	@Column(name="active", unique=false, nullable=false)
	private boolean active;
	
	@Column(name="token", unique=false, nullable=true)
	private String token;
	
	public User() {}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
