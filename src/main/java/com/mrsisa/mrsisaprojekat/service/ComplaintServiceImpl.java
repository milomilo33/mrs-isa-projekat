package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Complaint;
import com.mrsisa.mrsisaprojekat.repository.ComplaintRepositoryDB;

@Service
public class ComplaintServiceImpl implements ComplaintService{

	
	@Autowired
	private ComplaintRepositoryDB complaintRepository;
	
	@Override
	public Collection<Complaint> findAll() {
		return complaintRepository.findAll();
	}

	@Override
	public Collection<Complaint> findAllUnanswered() {
		return complaintRepository.findByResponseIsNull();
	}

	@Override
	public Collection<Complaint> findAllAnswered() {
		return complaintRepository.findByResponseIsNotNull();
	}

	@Override
	public Complaint create(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	public Complaint update(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	public void delete(Long id) {
		complaintRepository.deleteById(id);
		
	}

}
