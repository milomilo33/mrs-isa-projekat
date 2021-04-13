package com.mrsisa.mrsisaprojekat.service;

import java.util.List;

import com.mrsisa.mrsisaprojekat.model.Dermatologist;

public interface DermatologistService {
	List<Dermatologist> findAll();
	
	Dermatologist findOne(String email);
	
	Dermatologist create(Dermatologist dermatologist) throws Exception;
	
	Dermatologist update(Dermatologist dermatologist) throws Exception;
	
	boolean delete(String email);
}
