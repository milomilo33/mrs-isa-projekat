package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.repository.AddressRepositoryDB;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired 
	AddressRepositoryDB addressRepository;

	@Override
	public Collection<Address> findAll() {
		Collection<Address> addresses = addressRepository.findAll();
		return addresses;
	}

	@Override
	public Address findOne(Long id) {
		Address address = addressRepository.findById(id).orElseGet(null);
		return address;
	}

	@Override
	public Address create(Address address) throws Exception {
		if(address.getId() != null) {
			return null;
		}
		Address savedAddress = addressRepository.save(address);
		return savedAddress;
	}

	@Override
	public Address update(Address address) throws Exception {
		Address addressToUpdate = findOne(address.getId());
		if (addressToUpdate == null) {
			return null;
		}
		addressRepository.save(address);
		return address;
	}

	@Override
	public void delete(Long id) {
		addressRepository.deleteById(id);
		
	}
}
