package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.repository.PharmacyAdminRepositoryDB;

@Service
public class PharmacyAdminServiceImpl implements PharmacyAdminService{

	@Autowired
	private PharmacyAdminRepositoryDB adminRepository;
	
	@Override
	public List<AdminPharmacy> findAll() {
		List<AdminPharmacy> admins = adminRepository.getAllWithAddress();
		return admins;
	}

	@Override
	public AdminPharmacy findOne(String id) {
		AdminPharmacy admin = adminRepository.findById(id).orElseGet(null);
		return admin;
	}

	@Override
	public AdminPharmacy create(AdminPharmacy admin) throws Exception {
		return adminRepository.save(admin);
	}

	@Override
	public AdminPharmacy update(AdminPharmacy admin) throws Exception {
		AdminPharmacy adminToUpdate = adminRepository.findById(admin.getEmail()).orElseGet(null);
		if (adminToUpdate == null) {
			return null;
		}
		
		return adminRepository.save(admin);
	}

	@Override
	public void delete(String id) {
		adminRepository.deleteById(id);
	}
}
