package com.mrsisa.mrsisaprojekat.repository;

import com.mrsisa.mrsisaprojekat.model.Appointment;

import java.util.Optional;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;


public interface AppointmentRepositoryDB extends JpaRepository<Appointment, Long> {
	
	@Query("select a from Appointment a join fetch a.patient join fetch a.chosenEmployee where a.id=?1")
	Appointment getOneAppointmentWithPatient(Long id);
	
	
	@Query("select a from Appointment a join fetch a.chosenEmployee where a.chosenEmployee.email=?1")
	Set<Appointment> getOneAppointmentWithEmployee(String email);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from Appointment a where a.id =?1")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Appointment findOneAppointment(Long id);
	
}
