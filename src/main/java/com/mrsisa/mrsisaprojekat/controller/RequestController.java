package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.RequestDTO;
import com.mrsisa.mrsisaprojekat.dto.SendRequestDTO;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Request;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value= "/api/request")
public class RequestController {
	
	@Autowired
	private RequestService requestService;
	@Autowired
	private EmailService mailService;
	
	@Transactional(readOnly = false)
	@PutMapping(value= "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<RequestDTO> updateRequest(@RequestBody RequestDTO request,@PathVariable("id") Long id) throws Exception {
		Request r = requestService.findOneRequest(id);
	
		if(r !=null) {
			try {
					r.setAccepted(request.isAccepted());
					r.setDeleted(true);
					r.setRejectionReason(request.getRejectionReason());
					requestService.update(r);
					mailService.sendEmployeeMail(r.getEmployee(), r);
					return new ResponseEntity<>(new RequestDTO(r),HttpStatus.OK);
				
				
			}catch (NullPointerException e) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
	}

	@GetMapping(value = "/dermatologist")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	public ResponseEntity<Collection<Request>> getRequestsForDermatologist() {
		Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Collection<Request> requests = requestService.getRequestsForDermatologist(currentDermatologist);

		if (requests == null || requests.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(requests, HttpStatus.OK);
	}

	@PostMapping(value = "/dermatologist")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST')")
	public ResponseEntity<Object> makeRequestForDermatologist(@RequestBody SendRequestDTO sendRequestDTO) {
		Dermatologist currentDermatologist = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String message = requestService.makeRequestForDermatologist(currentDermatologist.getEmail(), sendRequestDTO);

		if (message != null) {
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/pharmacist")
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<Collection<Request>> getRequestsForPharmacist() {
		Pharmacist currentPharmacist = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Collection<Request> requests = requestService.getRequestsForPharmacist(currentPharmacist);

		if (requests == null || requests.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(requests, HttpStatus.OK);
	}

	@PostMapping(value = "/pharmacist")
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<Object> makeRequestForPharmacist(@RequestBody SendRequestDTO sendRequestDTO) {
		Pharmacist currentPharmacist = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String message = requestService.makeRequestForPharmacist(currentPharmacist.getEmail(), sendRequestDTO);

		if (message != null) {
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
