package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.AdminSystem;

public interface SystemAdminDB extends JpaRepository<AdminSystem, String>{

	@Query("select a from AdminSystem a join fetch a.address")
	List<AdminSystem> getAllWithAddress();
	
	@Query("select a from AdminSystem a join fetch a.roles where a.email=?1")
	AdminSystem getOneLogin(String id);
	
	@Query("select a from AdminSystem a join fetch a.address where a.email=?1")
	AdminSystem getOneWithComplaints(String email);
}
