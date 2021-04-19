package com.mrsisa.mrsisaprojekat.model;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import  javax.persistence.*;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="name")
	String name;
	
	@JsonIgnore
	@Override
	public String getAuthority() {
		return name;
	}
	
	public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
