package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import com.mrsisa.mrsisaprojekat.model.WorkHour;

public interface WorkHourService {
	
	Collection<WorkHour> findAll();
	
	WorkHour findOne(Long id);
	
	WorkHour create(WorkHour workHour) throws Exception;
	
	WorkHour update(WorkHour workHour) throws Exception;
	
	void delete(Long id);

}
