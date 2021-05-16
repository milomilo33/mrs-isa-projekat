package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.AdminSystem;

public interface SystemAdminService {
	
	Collection<AdminSystem> findAll();
	
	AdminSystem findOne(String id);
	
	AdminSystem findOneWithCompalints(String email);
	
	AdminSystem create(AdminSystem admin) throws Exception;
	
	AdminSystem update(AdminSystem admin) throws Exception;
	
	void delete(String id);
}
