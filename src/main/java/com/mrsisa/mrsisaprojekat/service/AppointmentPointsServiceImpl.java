package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.AppointmentPoints;
import com.mrsisa.mrsisaprojekat.repository.AppointmentPointsRepositoryDB;

@Service
public class AppointmentPointsServiceImpl implements AppointmentPointsService{

	@Autowired
	private AppointmentPointsRepositoryDB pointsRepository;
	
	@Override
	public Collection<AppointmentPoints> findAll() {
		return pointsRepository.findAll();
	}

	@Override
	public AppointmentPoints findOne(Long id) {
		return pointsRepository.findById(id).orElse(null);
	}

	@Override
	public AppointmentPoints update(AppointmentPoints appointmentPoints) {
		return pointsRepository.save(appointmentPoints);
	}

}
