package com.mrsisa.mrsisaprojekat.repository;

import com.mrsisa.mrsisaprojekat.model.Appointment;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AppointmentRepositoryDB extends JpaRepository<Appointment, Long> {
	
	@Query("select a from Appointment a join fetch a.patient join fetch a.chosenEmployee where a.id=?1")
	Appointment getOneAppointmentWithPatient(Long id);
	
	
	@Query("select a from Appointment a join fetch a.chosenEmployee where a.chosenEmployee.email=?1")
	Set<Appointment> getOneAppointmentWithEmployee(String email);
}
