package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Role;

public interface RoleService {
	Role findById(Long id);
	List<Role> findByName(String name);
}
