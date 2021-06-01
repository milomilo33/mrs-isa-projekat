package com.mrsisa.mrsisaprojekat.service;

import java.util.List;
import java.util.Set;

import com.mrsisa.mrsisaprojekat.dto.AdminPharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;

public interface PharmacyAdminService {
	List<AdminPharmacy> findAll();
	
	AdminPharmacy findOne(String id);
	
	AdminPharmacy create(AdminPharmacy admin) throws Exception;
	
	AdminPharmacy update(AdminPharmacy admin) throws Exception;
	
	void delete(String id);
	
	AdminPharmacy findOneWithRequestMedicaments(String email);
	
	boolean check(String password1, String password);
	
	Set<AdminPharmacy> getAllAdminsInPharmacy(Long id);
	
	List<AdminPharmacy> getAllUnemployedAdmins();

	AdminPharmacy updatePharmacy(AdminPharmacyDTO adminDTO);

	AdminPharmacy firePharmacyAdmin(PharmacyDTO pharmacyDTO);
}

