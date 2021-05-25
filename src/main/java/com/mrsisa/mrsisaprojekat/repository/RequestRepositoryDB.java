package com.mrsisa.mrsisaprojekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Request;

public interface RequestRepositoryDB extends JpaRepository<Request, Long> {

	@Query("select r from Request r join fetch r.employee where r.id = ?1")
    Request getOneWithEmployee(Long id);
}
