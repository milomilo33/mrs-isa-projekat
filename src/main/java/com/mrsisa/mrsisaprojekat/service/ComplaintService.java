package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Complaint;

public interface ComplaintService {

	Collection<Complaint> findAll();
	
	Collection<Complaint> findAllUnanswered();
	
	Collection<Complaint> findAllAnswered();
	
	Complaint create(Complaint complaint);
	
	Complaint update(Complaint complaint);
	
	void delete(Long id);
	
}
