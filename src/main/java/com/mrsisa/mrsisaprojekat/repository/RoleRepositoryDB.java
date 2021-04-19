package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrsisa.mrsisaprojekat.model.Role;

public interface RoleRepositoryDB extends JpaRepository<Role, Long>{
	List<Role> findByName(String name);
}
