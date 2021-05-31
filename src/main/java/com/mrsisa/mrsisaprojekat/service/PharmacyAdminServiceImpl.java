package com.mrsisa.mrsisaprojekat.service;

import java.util.List;
import java.util.Set;

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
		if (adminToUpdate == null) {
			return null;
		}
		
		return adminRepository.save(admin);
	}

	@Override
	public void delete(String id) {
		adminRepository.deleteOne(id);;
	}

	@Override
	public AdminPharmacy findOneWithRequestMedicaments(String email) {
		AdminPharmacy admin = adminRepository.getOneWithRequestMedicaments(email);
		return admin;
	}

	@Override
	public boolean check(String password1, String password) {
		if(!password1.equals(passwordEncoder.encode(password))){
			return false;
		}
		return true;
	}

	@Override
	public Set<AdminPharmacy> getAllAdminsInPharmacy(Long id) {
		return adminRepository.getAllEmployeedInPharmacy(id);
	}
}
