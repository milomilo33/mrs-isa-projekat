package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;

public interface PharmacyAdminService {
	List<AdminPharmacy> findAll();
	
	AdminPharmacy findOne(String id);
	
	AdminPharmacy create(AdminPharmacy admin) throws Exception;
	
	AdminPharmacy update(AdminPharmacy admin) throws Exception;
	
	void delete(String id);
	
	AdminPharmacy findOneWithRequestMedicaments(String email);
	
	boolean check(String password1, String password);
}

