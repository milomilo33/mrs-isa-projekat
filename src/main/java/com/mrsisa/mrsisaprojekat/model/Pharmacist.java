package com.mrsisa.mrsisaprojekat.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Pharmacist extends Employee {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// potencijalno kaskadiranje
	@OneToMany(mappedBy = "chosenEmployee", fetch = FetchType.LAZY)
	private Set<Appointment> counselings;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.MERGE, optional = true)
	private Pharmacy pharmacy;
	
	public Pharmacist() {}

	public Set<Appointment> getCounselings() {
		return counselings;
	}

	public void setCounselings(Set<Appointment> counselings) {
		this.counselings = counselings;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	

}
