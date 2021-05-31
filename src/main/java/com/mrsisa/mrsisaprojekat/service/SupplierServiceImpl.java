package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Role;
import com.mrsisa.mrsisaprojekat.model.Supplier;
import com.mrsisa.mrsisaprojekat.repository.SupplierRepositoryDB;

@Service
public class SupplierServiceImpl implements SupplierService{

	@Autowired
	private SupplierRepositoryDB supplierRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	@Override
	public Collection<Supplier> findAll() {
		List<Supplier> supplier = supplierRepository.getAllWithAddress();
		return supplier;
	}

	@Override
	public Supplier findOne(String id) {
		Supplier supplier = supplierRepository.findById(id).orElseGet(null);
		return supplier;
	}

	@Override
	public Supplier create(Supplier supplier) throws Exception {

		supplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_SUPPLIER");
		supplier.setRoles(roles);
		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier update(Supplier supplier) throws Exception {
		Supplier supplierToUpdate = supplierRepository.findById(supplier.getEmail()).orElseGet(null);
		if (supplierToUpdate == null) {
			return null;
		}
		
		return supplierRepository.save(supplier);
	}

	@Override
	public void delete(String id) {
		supplierRepository.deleteOne(id);
		
	}

}
