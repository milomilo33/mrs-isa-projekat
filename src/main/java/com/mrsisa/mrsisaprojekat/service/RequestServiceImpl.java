package com.mrsisa.mrsisaprojekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Request;
import com.mrsisa.mrsisaprojekat.repository.RequestRepositoryDB;

@Service
public class RequestServiceImpl implements RequestService{
	
	@Autowired
    private RequestRepositoryDB requestRepository;

	@Override
	public Request update(Request request) throws Exception {
		Request r = requestRepository.getOneWithEmployee(request.getId());
		
		if (r == null) {
			return null;
		}
		
		return requestRepository.save(request);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Request findOneRequest(Long id) {
		Request r = requestRepository.getOneWithEmployee(id);
		
		if (r == null) {
			return null;
		}
		return r;
	}

}
