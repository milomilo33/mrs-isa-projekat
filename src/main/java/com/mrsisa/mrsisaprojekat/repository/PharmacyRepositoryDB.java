package com.mrsisa.mrsisaprojekat.repository;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.mrsisa.mrsisaprojekat.model.Pharmacy;

@Repository
public class PharmacyRepositoryDB implements PharmacyRepository{

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Pharmacy> pharmacies = new ConcurrentHashMap<Long, Pharmacy>();
	
	@Override
	public Collection<Pharmacy> findAll() {
		return this.pharmacies.values();
	}

	@Override
	public Pharmacy create(Pharmacy pharmacy) {
		Long id = pharmacy.getId();
		
		if(id == null) {
			id = counter.incrementAndGet();
			pharmacy.setId(id);
		}
		
		this.pharmacies.put(id, pharmacy);
		return pharmacy;
	}

	@Override
	public Pharmacy findOne(Long id) {
		return this.pharmacies.get(id);
	}

	@Override
	public Pharmacy update(Pharmacy pharmacy) {
		Long id = pharmacy.getId();
		
		if(id != null) {
			this.pharmacies.put(id, pharmacy);
		}
		
		return pharmacy;
	}

	@Override
	public void delete(Long id) {
		this.pharmacies.remove(id);
		
	}
}
