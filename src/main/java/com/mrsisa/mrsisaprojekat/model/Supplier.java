package com.mrsisa.mrsisaprojekat.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Supplier extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// potencijalno kaskadiranje
	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
	private Set<Offer> offers;
	
	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
	private Set<Order> orders;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<MedicamentItem> medicamentItems;
	
	public Supplier() {}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<MedicamentItem> getMedicaments() {
		return medicamentItems;
	}

	public void setMedicaments(Set<MedicamentItem> medicaments) {
		this.medicamentItems = medicaments;
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
