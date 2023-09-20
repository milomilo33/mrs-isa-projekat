package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;

@Service
public interface PricelistItemAppointmentService {
	
	Collection<PricelistItemAppointment> findAllPharmacy(Long id);;
	
	PricelistItemAppointment findOne(Long id);
	
	PricelistItemAppointment create(PricelistItemAppointment pricelistItem) throws Exception;
	
	PricelistItemAppointment update(PricelistItemAppointment pricelistItem) throws Exception;
	
	void delete(Long id);
	
	void restore(Long id);
	
	PricelistItemAppointment findOnePricelistItemAppointment(Long id);


}
