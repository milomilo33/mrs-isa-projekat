package com.mrsisa.mrsisaprojekat.service;

import org.springframework.stereotype.Service;
import com.mrsisa.mrsisaprojekat.model.Request;

@Service
public interface RequestService {
	
	Request update(Request request) throws Exception;
	
	void delete(Long id);
	
	Request findOneRequest(Long id);

}
