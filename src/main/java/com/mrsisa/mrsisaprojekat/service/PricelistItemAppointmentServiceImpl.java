package com.mrsisa.mrsisaprojekat.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;
import com.mrsisa.mrsisaprojekat.repository.PriceRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PricelistItemAppointmentRepositoryDB;

@Service
public class PricelistItemAppointmentServiceImpl implements PricelistItemAppointmentService {
	
	@Autowired
	private PricelistItemAppointmentRepositoryDB pricelistItemRepository;

	@Autowired
	private PriceRepositoryDB priceRepository;
	@Override
	public Set<PricelistItemAppointment> findAllPharmacy(Long id) {
		Set<PricelistItemAppointment> items =  pricelistItemRepository.findAllPricelistItemAppointments(id);
		return items;
	}


	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PricelistItemAppointment findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PricelistItemAppointment create(PricelistItemAppointment pricelistItem) throws Exception {
		return pricelistItemRepository.save(pricelistItem);
	}


	@Override
	public PricelistItemAppointment update(PricelistItemAppointment pricelistItem) throws Exception {
		PricelistItemAppointment item = pricelistItemRepository.findOnePricelistItemAppointments(pricelistItem.getId());
		if(item == null) {
			return null;
		}
		pricelistItemRepository.save(pricelistItem);
		return item;
		
	}


	@Override
	public PricelistItemAppointment findOnePricelistItemAppointment(Long id) {
		PricelistItemAppointment item = pricelistItemRepository.findOnePricelistItemAppointments(id);
		if(item == null) {
			return null;
		}
		return item;
	}
}