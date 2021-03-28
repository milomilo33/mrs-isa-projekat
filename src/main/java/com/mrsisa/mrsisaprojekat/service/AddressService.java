package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Address;

public interface AddressService {
	Collection<Address> findAll();
	
	Address findOne(Long id);
	
	Address create(Address address) throws Exception;
	
	Address update(Address address) throws Exception;
	
	void delete(Long id);
}
