package com.mrsisa.mrsisaprojekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mrsisa.mrsisaprojekat.dto.MedicamentItemDTO;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.service.MedicamentItemService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/medicamentItems")
public class MedicamentItemController {
	
	
	@Autowired
	private MedicamentItemService medicamentItemService;
	
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<MedicamentItemDTO> saveMedicament(@RequestBody MedicamentItem medicamentItem) throws Exception{
		MedicamentItem m = medicamentItemService.findOne(medicamentItem.getId());
		if(m != null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		medicamentItem = medicamentItemService.create(medicamentItem);
		return new ResponseEntity<>(new MedicamentItemDTO(medicamentItem), HttpStatus.CREATED); 
		
		
	}
	
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<MedicamentItemDTO> deleteMedicamentItem(@PathVariable("id") Long id) {

		MedicamentItem medicamentItem = medicamentItemService.findOne(id);
		
		if (medicamentItem != null) {
			medicamentItemService.delete(id) ;
			return new ResponseEntity<>(new MedicamentItemDTO(medicamentItem),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}


}
