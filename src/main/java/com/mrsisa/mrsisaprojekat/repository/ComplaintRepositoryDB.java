package com.mrsisa.mrsisaprojekat.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Complaint;

public interface ComplaintRepositoryDB extends JpaRepository<Complaint, Long>{

	
	@Query("select c from Complaint c join fetch c.employee where c.id=?1")
	Complaint findOneWithEmployee(Long id);
	
	@Query("select c from Complaint c join fetch c.pharmacy where c.id=?1")
	Complaint findOneWithPharmacy(Long id);
	
	@Query("select c from Complaint c join fetch c.patient where c.response is null")
	Collection<Complaint> findByResponseIsNull();

	@Query("select c from Complaint c left join fetch c.responder where c.response is not null")
	Collection<Complaint> findByResponseIsNotNull();
	
	@Query("select c from Complaint c join fetch c.responder where c.responder.email=?1")
	Set<Complaint> findByResponder(String email);

}
