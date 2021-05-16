package com.mrsisa.mrsisaprojekat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mrsisa.mrsisaprojekat.model.Complaint;

public interface ComplaintRepositoryDB extends JpaRepository<Complaint, Long>{

	Collection<Complaint> findByResponseIsNull();

	Collection<Complaint> findByResponseIsNotNull();
	
	
	

}
