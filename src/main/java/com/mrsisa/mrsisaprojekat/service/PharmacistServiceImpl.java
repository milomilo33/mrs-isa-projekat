package com.mrsisa.mrsisaprojekat.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Role;
import com.mrsisa.mrsisaprojekat.repository.PharmacistRepositoryDB;

@Service
public class PharmacistServiceImpl  implements PharmacistService {

	@Autowired
	private PharmacistRepositoryDB pharmacistRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	@Override
	public List<Pharmacist> findAll() {
		List<Pharmacist> pharmacists = pharmacistRepository.getAllWithAddress();
		return pharmacists;
	}

	@Override
	@Transactional
	public Pharmacist findOne(String email) {
		Pharmacist pharmacist = pharmacistRepository.getOnePharmacist(email);
		return pharmacist;
	}

	@Override
	public Pharmacist create(Pharmacist pharmacist) throws Exception {
		pharmacist.setPassword(passwordEncoder.encode(pharmacist.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_PHARMACIST");
		pharmacist.setRoles(roles);
		return pharmacistRepository.save(pharmacist);
	}

	@Override
	public Pharmacist update(Pharmacist pharmacist) throws Exception {
		Pharmacist pharmacistToUpdate = pharmacistRepository.findById(pharmacist.getEmail()).orElseGet(null);
		if (pharmacistToUpdate == null) {
			return null;
		}
		pharmacistRepository.save(pharmacist);
		return pharmacist;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(String email) {
		// potrebno je proveriti da li ima zakazana savetovanja, ukoliko ih ima ne moze da se obrise
		Pharmacist pharmacist = pharmacistRepository.getOnePharmacist(email);
		try {
			if(pharmacist.getCounselings().size()== 0) {	
				pharmacistRepository.deleteOne(email);
				return true;
			}
			return false;
		}catch(NullPointerException e) {
			return false;
		}
		
	}

	@Override
	public List<Appointment> getAvailableAppointments(Pharmacist p) {
		List<Appointment> availableAppointments = new ArrayList<>();
		Pharmacist pharmacist = pharmacistRepository.getPharmacistWithCounselings(p.getEmail());
		for(Appointment a : pharmacist.getCounselings()) {
			if(a.getPatient() == null) {
				availableAppointments.add(a);
			}
		}
		return availableAppointments;
	}

	@Override
	public Pharmacist findOneCounselings(String email) {
		Pharmacist p = pharmacistRepository.getPharmacistWithCounselings(email);

		return p;
	}


}
