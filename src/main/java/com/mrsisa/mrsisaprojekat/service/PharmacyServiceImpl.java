package com.mrsisa.mrsisaprojekat.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.mrsisa.mrsisaprojekat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;

@Service

public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;
	
	@Override
	public List<Pharmacy> findAll() {
		List<Pharmacy> pharmacies = pharmacyRepository.getAllWithAddress();
		return pharmacies;
	}

	@Override
	public Pharmacy findOne(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOnePharmacy(id);
		return pharmacy;
	}

	@Override
	public Pharmacy create(Pharmacy pharmacy) throws Exception {
		if(pharmacy.getId() != null) {
			return null;
		}
		Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
		return savedPharmacy;
	}
	@Override
	public Pharmacy update(Pharmacy pharmacy) throws Exception {
		Pharmacy pharmacyToUpdate = pharmacyRepository.findById(pharmacy.getId()).orElseGet(null);
		if (pharmacyToUpdate == null) {
			return null;
		}
		pharmacyRepository.save(pharmacy);
		return pharmacy;
	}

	@Override
	public void delete(Long id) {
		pharmacyRepository.deleteById(id);
		
	}

	@Override
	public List<MedicamentItem> getAllMedicaments(Long id) {
		List<MedicamentItem> items = pharmacyRepository.getAllMedicaments(id);
		if(items == null) {
			return null;
		}
		return items;
	}

	@Override
	public Pharmacy findOneWithMedicaments(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithMedicaments(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}
	
	@Override
	public Pharmacy findOneWithDermatologists(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithDermatologists(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}

	@Override
	public Pharmacy findOneWithPharmacists(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithPharmacists(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}

	@Override
	public int getRating(Long id) {
		List<Rating> ratings = pharmacyRepository.getRatings(id);
		int i = 0;
		int sum = 0;

		for(Rating r : ratings) {
			sum += r.getValue();
			i++;
		}
		if(i == 0) return 0;

		return Math.round(sum / i);
	}

	@Override
	public Pharmacy findOneWithAppointments(Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithAppointments(id);
		if (pharmacy == null) {
			return null;
		}
	
		return pharmacy;
	}
	
	@Override
	public ArrayList<Appointment> findAllAppointmentsDeramtologist(String email, Long id) {
		Pharmacy pharmacy = pharmacyRepository.getOneWithAppointments(id);
		if (pharmacy == null) {
			return null;
		}
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		for(Appointment a : pharmacy.getAppointments()) {
			if(a.getChosenEmployee().getEmail().equals(email)) {
				appointments.add(a);
			}
		}
	
		return appointments;
	}

	@Override
	public void addRating(Rating rating, Long ratedEntityId) {
		Pharmacy pharmacy = pharmacyRepository.loadWithRatings(ratedEntityId);

		try {
			pharmacy.getRatings().add(rating);
		} catch (NullPointerException e) {
			HashSet<Rating> ratings = new HashSet<>();
			ratings.add(rating);
			pharmacy.setRatings(ratings);
		}


		pharmacyRepository.save(pharmacy);
	}


}
