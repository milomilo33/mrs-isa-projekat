package com.mrsisa.mrsisaprojekat.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.mrsisa.mrsisaprojekat.model.Request;

public interface RequestRepositoryDB extends JpaRepository<Request, Long> {

	//@Lock(value = LockModeType.OPTIMISTIC)
	//@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="4000")})
	@Query("select r from Request r join fetch r.employee where r.id = ?1")
    Request getOneWithEmployee(Long id);
}
