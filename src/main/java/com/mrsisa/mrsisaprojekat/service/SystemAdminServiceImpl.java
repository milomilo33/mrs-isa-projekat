package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Role;
import com.mrsisa.mrsisaprojekat.repository.SystemAdminDB;

@Service
public class SystemAdminServiceImpl implements SystemAdminService{

	@Autowired
	private SystemAdminDB adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	@Override
	public Collection<AdminSystem> findAll() {
		Collection<AdminSystem> admins = adminRepository.findAll();
		return admins;
	}

	@Override
	public AdminSystem findOne(String id) {
		AdminSystem admin = adminRepository.findById(id).orElseGet(null);
		return admin;
	}

	@Override
	public AdminSystem create(AdminSystem admin) throws Exception {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_SYSTEM_ADMIN");
		admin.setRoles(roles);
		return adminRepository.save(admin);
	}

	@Override
	public AdminSystem update(AdminSystem admin) throws Exception {
		AdminSystem adminToUpdate = adminRepository.findById(admin.getEmail()).orElseGet(null);
		if (adminToUpdate == null) {
			return null;
		}
		
		return adminRepository.save(admin);
	}

	@Override
	public void delete(String id) {
		adminRepository.deleteOne(id);
		
	}

	@Override
	public AdminSystem findOneWithCompalints(String email) {
		return adminRepository.getOneWithComplaints(email);
	}

}
