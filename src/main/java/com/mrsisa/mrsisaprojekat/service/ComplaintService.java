package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.Set;

import com.mrsisa.mrsisaprojekat.dto.ComplaintDTO;
import com.mrsisa.mrsisaprojekat.model.Complaint;

public interface ComplaintService {

	Collection<Complaint> findAll();
	
	Collection<Complaint> findAllUnanswered();
	
	Collection<Complaint> findAllAnswered();
	
	Collection<Complaint> findPatientnsUnasnswered(String email);
	
	Complaint findOne(Long id);
	
	Set<Complaint> getResponderComplaint(String email);
	
	Complaint create(Complaint complaint);
	
	Complaint update(Complaint complaint);
	
	void delete(Long id);

	Complaint findOneWithEmployee(Long id);

	Complaint findOneWithPharmacy(Long id);

	ComplaintDTO writeResponse(ComplaintDTO complaintDTO);
	
}
