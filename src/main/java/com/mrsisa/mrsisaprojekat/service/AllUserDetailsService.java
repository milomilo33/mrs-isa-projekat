package com.mrsisa.mrsisaprojekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.User;
import com.mrsisa.mrsisaprojekat.repository.DermatologistRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacistRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyAdminRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.SystemAdminDB;

@Service
public class AllUserDetailsService implements UserDetailsService {

	@Autowired
	private PatientRepositoryDB patientRepository;
	
	@Autowired
	private SystemAdminDB systemAdminRepository;
	
	@Autowired
	private PharmacyAdminRepositoryDB pharmacyAdminRepository;
	
	@Autowired
	private PharmacistRepositoryDB pharmacistRepository;
	
	@Autowired
	private DermatologistRepositoryDB dermatologistRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = patientRepository.getOneLogin(username);
		if(user != null) {
			return user;
		}
		
		user = systemAdminRepository.getOneLogin(username);
		if(user!=null) {
			return user;
		}
		
		user = pharmacistRepository.getOneLogin(username);
		if(user != null) {
			return user;
		}
		
		user = pharmacyAdminRepository.getOneLogin(username);
		if(user != null) {
			return user;
		}
		
		user = dermatologistRepository.getOneLogin(username);
		if(user != null) {
			return user;
		}
		
		// Treba dodati za suppliera
		// user = supplierRepository.findById(username).orElse(null);
		// if(user != null){
		// return user;}
		
		throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		
	}

}
