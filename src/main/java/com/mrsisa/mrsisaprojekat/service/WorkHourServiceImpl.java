package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.WorkHour;
import com.mrsisa.mrsisaprojekat.repository.WorkHourRepositoryDB;

@Service
public class WorkHourServiceImpl implements WorkHourService {
	
	@Autowired 
	WorkHourRepositoryDB workHourRepository;


	@Override
	public Collection<WorkHour> findAll() {
		List<WorkHour> workHours = workHourRepository.getAllWithPharmacy();
		return workHours;
	}

	@Override
	public WorkHour findOne(Long id) {
		WorkHour workHour = workHourRepository.getOneWorkHour(id);
		return workHour;
	}

	@Transactional
	@Override
	public WorkHour create(WorkHour workHour) throws Exception {
		if(workHour.getId() != null) {
			return null;
		}
		WorkHour savedworkHour = workHourRepository.save(workHour);
		return savedworkHour;
	}

	@Override
	public WorkHour update(WorkHour workHour) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
