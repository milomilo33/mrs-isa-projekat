package com.mrsisa.mrsisaprojekat.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.Role;
import com.mrsisa.mrsisaprojekat.repository.PharmacyAdminRepositoryDB;

@Service
public class PharmacyAdminServiceImpl implements PharmacyAdminService{

	@Autowired
	private PharmacyAdminRepositoryDB adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	@Override
	public List<AdminPharmacy> findAll() {
		List<AdminPharmacy> admins = adminRepository.getAllWithAddress();
		return admins;
	}

	@Override
	public AdminPharmacy findOne(String id) {
		AdminPharmacy admin = adminRepository.getOnePharmacyAdmin(id);
		return admin;
	}

	@Override
	public AdminPharmacy create(AdminPharmacy admin) throws Exception {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_PHARMACY_ADMIN");
		admin.setRoles(roles);
		return adminRepository.save(admin);
	}

	@Override
	public AdminPharmacy update(AdminPharmacy admin) throws Exception {
		AdminPharmacy adminToUpdate = adminRepository.getOnePharmacyAdmin(admin.getEmail());
		System.out.println(adminToUpdate.getPharmacy().getName());
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
