package com.mrsisa.mrsisaprojekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.AdminSystem;

public interface SystemAdminDB extends JpaRepository<AdminSystem, String>{

	@Query("select a from AdminSystem a join fetch a.address where a.deleted = false")
	List<AdminSystem> getAllWithAddress();
	
	@Query("select a from AdminSystem a join fetch a.roles where a.email=?1 and a.deleted = false")
	AdminSystem getOneLogin(String id);
	
	@Modifying
	@Query("update AdminSystem a set a.deleted = true where a.email=?1")
	void deleteOne(String email);
	
	@Query("select a from AdminSystem a join fetch a.address where a.email=?1 and a.deleted = false")
	AdminSystem getOneWithComplaints(String email);
}
