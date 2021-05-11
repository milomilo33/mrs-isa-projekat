package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.MedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.Rating;
import com.mrsisa.mrsisaprojekat.service.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/medicaments")
public class MedicamentController {
	
	@Autowired
	private MedicamentService service;
	
	@GetMapping(value="/all")
	public ResponseEntity<List<MedicamentDTO>> getMedicaments(){
		List<Medicament> medicaments = (List<Medicament>) service.findAll();
		List<MedicamentDTO> medicamentsDTO = new ArrayList<>();
		for(Medicament m : medicaments) {
			medicamentsDTO.add(new MedicamentDTO(m));
		}
		
		return new ResponseEntity<>(medicamentsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicamentDTO> getMedicament(@PathVariable("id") Long id) {
		
		Medicament medicament = service.findOne(id);

		if (medicament == null) {
			return new ResponseEntity<MedicamentDTO>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<MedicamentDTO>(new MedicamentDTO(medicament), HttpStatus.OK);
	}
	
	@GetMapping(value="/ratings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public double getRatings(@PathVariable("id") Long id) {
	
		Medicament m = service.getRatings(id);
		try {
			m.getRatings();
		}catch(NullPointerException e) {
			return 0;
		}
		
		
		double val = 0;
		
		for(Rating g : m.getRatings()) {
			val+=g.getValue();
		}
		val= val/m.getRatings().size();
		return val;
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'PHARMACY_ADMIN')")
	public ResponseEntity<MedicamentDTO> saveMedicament(@RequestBody Medicament medicament) throws Exception{
		Medicament temp = service.findOne(medicament.getId());
		if(temp != null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		medicament = service.create(medicament);
		return new ResponseEntity<>(new MedicamentDTO(medicament), HttpStatus.CREATED); 
		
		
	}
	@GetMapping(value = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<MedicamentDTO>> getSearchMedicament(@PathVariable("query") String query) {
		System.out.println(query);
		Collection<Medicament> medicaments = service.findAllWithName(query);

		if (medicaments == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Collection<MedicamentDTO> medicamentsDTO = new ArrayList<>();
		for(Medicament m : medicaments) {
			medicamentsDTO.add(new MedicamentDTO(m));
		}
	
		return new ResponseEntity<Collection<MedicamentDTO>>(medicamentsDTO, HttpStatus.OK);
	}
	@GetMapping(value = "/filter/mode={mode}&form={form}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<MedicamentDTO>> getFilterMedicament(@PathVariable("mode") int mode,@PathVariable("form") int form ) {
		Collection<Medicament> medicaments = service.findAll();

		if (medicaments == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Collection<MedicamentDTO> medicamentsDTO = new ArrayList<>();
		for(Medicament m : medicaments) {
			if(mode != -1 && form != -1) {
				if(m.getIssuanceMode().ordinal() == mode && m.getMedicamentForm().ordinal()==form)
					medicamentsDTO.add(new MedicamentDTO(m));
			}
			else if(mode != -1 && form == -1) {
				if(m.getIssuanceMode().ordinal() == mode)
					medicamentsDTO.add(new MedicamentDTO(m));
			}
			else if(mode == -1 && form!= -1) {
				if(m.getMedicamentForm().ordinal() == form)
					medicamentsDTO.add(new MedicamentDTO(m));
			}
			else {
				medicamentsDTO.add(new MedicamentDTO(m));
			}
		}
	
		return new ResponseEntity<Collection<MedicamentDTO>>(medicamentsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/substitutes")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<MedicamentDTO>> getNonallergicSubstituteMedicinesForPatientInPharmacyWithQuantity(
			@PathVariable("id") Long medicamentId, @RequestParam String patientEmail, @RequestParam int quantity,
			@RequestParam Long pharmacyId
	)
	{
		Collection<MedicamentDTO> substitutes = service.getNonallergicSubstituteMedicinesForPatientInPharmacyWithQuantity(
												medicamentId, patientEmail, quantity, pharmacyId);

		if (substitutes == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(substitutes, HttpStatus.OK);
	}
}
