package com.mrsisa.mrsisaprojekat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.dto.AdminPharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
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
	
	@Autowired
	private PharmacyService pharmacyService;
	
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

	@Override
	public List<AdminPharmacy> getAllUnemployedAdmins() {

		List<AdminPharmacy> retVal = adminRepository.getAllWithAddress();
		List<AdminPharmacy> returnAdmins = new ArrayList<>();
		for(AdminPharmacy a : retVal) {
			if(a.getPharmacy() == null) {
				returnAdmins.add(a);
			}
		}
		return returnAdmins;
	}

	@Override
	public AdminPharmacy updatePharmacy(AdminPharmacyDTO adminDTO) {
		AdminPharmacy admin = adminRepository.getOneLogin(adminDTO.getEmail());

		if(admin == null) {
			return null;
		}
		
		Pharmacy pharmacy = pharmacyService.findOne(adminDTO.getPharmacy().getId());
		if(pharmacy == null) {
			return null;
		}
		
		admin.setPharmacy(pharmacy);
		admin.setRequestMedicaments(null);
		admin = adminRepository.save(admin);

		Set<AdminPharmacy> admins = adminRepository.getAllEmployeedInPharmacy(pharmacy.getId());
		admins.add(admin);
		
		pharmacy.setAdmins(admins);
		try {
			pharmacyService.update(pharmacy);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return admin;
	}

	@Override
	public AdminPharmacy firePharmacyAdmin(PharmacyDTO pharmacyDTO) {
		
		Pharmacy pharmacy = pharmacyService.findOne(pharmacyDTO.getId());
		if(pharmacy == null || pharmacyDTO.getAdmins() == null) {
			return null;
		}
		
		String adminEmail = "";
		for(String a : pharmacyDTO.getAdmins()) {
			adminEmail = a;
			break;
		}
		
		AdminPharmacy admin = adminRepository.getOnePharmacyAdmin(adminEmail);
		if(admin == null) {
			return null;
		}
		
		admin.setPharmacy(null);
		admin.setRequestMedicaments(null);
		admin = adminRepository.save(admin);

		Set<AdminPharmacy> admins = adminRepository.getAllEmployeedInPharmacy(pharmacy.getId());
		for(AdminPharmacy a : admins) {
			if(a.getEmail().equalsIgnoreCase(admin.getEmail())) {
				admins.remove(a);
				break;
			}
		}
		
		pharmacy.setAdmins(admins);
		try {
			pharmacyService.update(pharmacy);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return admin;
	}
}
