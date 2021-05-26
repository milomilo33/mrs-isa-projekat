package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.SendRequestDTO;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Request;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface RequestService {

	Request findOne(Long id);

	List<Request> findAll();
	
	Request update(Request request) throws Exception;
	
	void delete(Long id);
	
	Request findOneRequest(Long id);

	Collection<Request> getRequestsForDermatologist(Dermatologist dermatologist);

	String makeRequestForDermatologist(String dermatologistEmail, SendRequestDTO sendRequestDTO);

}
