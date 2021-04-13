package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.MedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.Rating;
import com.mrsisa.mrsisaprojekat.service.MedicamentService;

@RestController
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
	public ResponseEntity<MedicamentDTO> saveMedicament(@RequestBody Medicament medicament) throws Exception{
		Medicament temp = service.findOne(medicament.getId());
		if(temp != null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		medicament = service.create(medicament);
		return new ResponseEntity<>(new MedicamentDTO(medicament), HttpStatus.CREATED); 
		
		
	}
}
