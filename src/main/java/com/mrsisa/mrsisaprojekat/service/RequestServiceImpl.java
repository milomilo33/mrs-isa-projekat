package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.SendRequestDTO;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Request;
import com.mrsisa.mrsisaprojekat.repository.RequestRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService{
	
	@Autowired
    private RequestRepositoryDB requestRepository;

	@Autowired
	private DermatologistService dermatologistService;

	@Autowired
	private PharmacistService pharmacistService;

	@Override
	@Transactional
	public Request findOne(Long id) {
		return requestRepository.findById(id).orElse(null);
	}

	@Override
	public List<Request> findAll() {
		return requestRepository.findAll();
	}

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

	@Override
	@Transactional(readOnly = true)
	public Collection<Request> getRequestsForDermatologist(Dermatologist dermatologist) {
		Collection<Request> requests = this.findAll();

		Collection<Request> dermatologistRequests = new HashSet<>();
		if (requests == null || requests.isEmpty()) {
			return dermatologistRequests;
		}

		for (Request r : requests) {
			if (r.getEmployee().getEmail().equals(dermatologist.getEmail())) {
				if (!r.isDeleted()) {
					r.setEmployee(null);
					dermatologistRequests.add(r);
				}
			}
		}

		return dermatologistRequests;
	}

	@Override
	public Collection<Request> getRequestsForPharmacist(Pharmacist pharmacist) {
		Collection<Request> requests = this.findAll();

		Collection<Request> pharmacistRequests = new HashSet<>();
		if (requests == null || requests.isEmpty()) {
			return pharmacistRequests;
		}

		for (Request r : requests) {
			if (r.getEmployee().getEmail().equals(pharmacist.getEmail())) {
				if (!r.isDeleted()) {
					r.setEmployee(null);
					pharmacistRequests.add(r);
				}
			}
		}

		return pharmacistRequests;
	}

	@Override
	@Transactional
	public String makeRequestForDermatologist(String dermatologistEmail, SendRequestDTO sendRequestDTO) {
		Dermatologist dermatologist = dermatologistService.findOne(dermatologistEmail);

		if (dermatologist == null) {
			return "Error";
		}

		LocalDate dateFrom = sendRequestDTO.getDateFrom();
		LocalDate dateTo = sendRequestDTO.getDateTo();
		String description = sendRequestDTO.getDescription();

		if (dateFrom == null || dateTo == null || description == null) {
			return "Invalid data!";
		}

		// provera da li je datum validan
		if (dateFrom.isAfter(dateTo)) {
			return "Invalid date!";
		}

		// provera da li se zahtev preklapa sa postojecim terminom dermatologa
		if (dermatologist.getMedicalExaminations() != null) {
			for (Appointment a : dermatologist.getMedicalExaminations()) {
				if (!a.isDeleted()) {
					// preklapanje
					if (a.getDate().isEqual(dateFrom) || a.getDate().isEqual(dateTo) ||
							(a.getDate().isAfter(dateFrom) && a.getDate().isBefore(dateTo))) {
						return "The dates overlap with one of your appointments!";
					}
				}
			}
		}

		if (dermatologist.getRequests() != null) {
			for (Request r : dermatologist.getRequests()) {
				if (!r.isDeleted()) {
					if ((dateFrom.isBefore(r.getDateTo()) || dateFrom.isEqual(r.getDateTo()))
							&& (dateTo.isAfter(r.getDateFrom()) ||  dateTo.isEqual(r.getDateFrom()))) {
						return "The dates overlap with an existing request!";
					}
				}
			}
		}

		Request request = new Request();
		request.setAccepted(false);
		request.setDeleted(false);
		request.setDateFrom(dateFrom);
		request.setDateTo(dateTo);
		request.setDescription(description);
		request.setEmployee(dermatologist);

		requestRepository.save(request);

		return null;
	}

	@Override
	@Transactional
	public String makeRequestForPharmacist(String pharmacistEmail, SendRequestDTO sendRequestDTO) {
		Pharmacist pharmacist = pharmacistService.findOne(pharmacistEmail);

		if (pharmacist == null) {
			return "Error";
		}

		LocalDate dateFrom = sendRequestDTO.getDateFrom();
		LocalDate dateTo = sendRequestDTO.getDateTo();
		String description = sendRequestDTO.getDescription();

		if (dateFrom == null || dateTo == null || description == null) {
			return "Invalid data!";
		}

		// provera da li je datum validan
		if (dateFrom.isAfter(dateTo)) {
			return "Invalid date!";
		}

		// provera da li se zahtev preklapa sa postojecim terminom farmaceuta
		if (pharmacist.getCounselings() != null) {
			for (Appointment a : pharmacist.getCounselings()) {
				if (!a.isDeleted()) {
					// preklapanje
					if (a.getDate().isEqual(dateFrom) || a.getDate().isEqual(dateTo) ||
							(a.getDate().isAfter(dateFrom) && a.getDate().isBefore(dateTo))) {
						return "The dates overlap with one of your appointments!";
					}
				}
			}
		}

		if (pharmacist.getRequests() != null) {
			for (Request r : pharmacist.getRequests()) {
				if (!r.isDeleted()) {
					if ((dateFrom.isBefore(r.getDateTo()) || dateFrom.isEqual(r.getDateTo()))
							&& (dateTo.isAfter(r.getDateFrom()) ||  dateTo.isEqual(r.getDateFrom()))) {
						return "The dates overlap with an existing request!";
					}
				}
			}
		}

		Request request = new Request();
		request.setAccepted(false);
		request.setDeleted(false);
		request.setDateFrom(dateFrom);
		request.setDateTo(dateTo);
		request.setDescription(description);
		request.setEmployee(pharmacist);

		requestRepository.save(request);

		return null;
	}

}
