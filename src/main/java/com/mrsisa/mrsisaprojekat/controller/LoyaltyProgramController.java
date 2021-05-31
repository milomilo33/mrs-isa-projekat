package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.mrsisa.mrsisaprojekat.dto.PatientLoyaltyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AppointmentPointsDTO;
import com.mrsisa.mrsisaprojekat.dto.CategoryDTO;
import com.mrsisa.mrsisaprojekat.dto.MedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.AppointmentPoints;
import com.mrsisa.mrsisaprojekat.model.AppointmentPoints.AppointmentType;
import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.service.AppointmentPointsService;
import com.mrsisa.mrsisaprojekat.service.CategoryThresholdsService;
import com.mrsisa.mrsisaprojekat.service.MedicamentService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/loyaltyProgram")
public class LoyaltyProgramController {
	
	@Autowired
	private CategoryThresholdsService thresholdService;
	
	@Autowired
	private AppointmentPointsService pointService;
	
	@Autowired
	private MedicamentService medicamentService;
	
	@GetMapping(value = "/getCategories")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<Collection<CategoryDTO>> getCategories(){
		Collection<CategoryThresholds> categories = thresholdService.findAll();
		Collection<CategoryDTO> categoriesDTO = new ArrayList<CategoryDTO>();
		
		for(CategoryThresholds c : categories) {
			if(!c.isDeleted()) {
				categoriesDTO.add(new CategoryDTO(c));
			}
		}
		
		return new ResponseEntity<>(categoriesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPoints")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<Collection<AppointmentPointsDTO>> getPoints(){
		Collection<AppointmentPoints> points = pointService.findAll();
		Collection<AppointmentPointsDTO> pointsDTO = new ArrayList<AppointmentPointsDTO>();
		
		for(AppointmentPoints c : points) {
			pointsDTO.add(new AppointmentPointsDTO(c));
		}
		
		return new ResponseEntity<>(pointsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "updateCategory")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO){
		CategoryThresholds category = thresholdService.findOne(categoryDTO.getId());
		
		if(category == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		category.setCategory(categoryDTO.getCategory());
		category.setThreshold(categoryDTO.getThreshold());
		category.setDiscount(categoryDTO.getDiscount());
		CategoryThresholds saved = thresholdService.update(category);
		return new ResponseEntity<>(new CategoryDTO(saved), HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/updatePoints")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<AppointmentPointsDTO> updatePoints(@RequestBody AppointmentPointsDTO pointDTO){
		
		AppointmentPoints points = pointService.findOne(pointDTO.getId());
		if(points == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		points.setPoints(pointDTO.getPoints());
		points.setType(AppointmentType.valueOf(pointDTO.getType()));
		
		AppointmentPoints saved = pointService.update(points);
		return new ResponseEntity<>(new AppointmentPointsDTO(saved), HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/updateMedicament")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<MedicamentDTO> updateMedicament(@RequestBody MedicamentDTO medicamentDTO){
		Medicament medicament = medicamentService.findOne(medicamentDTO.getId());
		if(medicament == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		medicament.setPoints(medicamentDTO.getPoints());
		
		Medicament saved = medicamentService.update(medicament);
		return new ResponseEntity<>(new MedicamentDTO(saved), HttpStatus.OK);
	}

	@PostMapping(value="/add")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO category){
		CategoryThresholds thresholds = new CategoryThresholds();
		
		thresholds.setCategory(category.getCategory());
		thresholds.setDiscount(category.getDiscount());
		thresholds.setThreshold(category.getThreshold());
		thresholds.setDeleted(false);
		thresholds = thresholdService.create(thresholds);
		return new ResponseEntity<>(new CategoryDTO(thresholds), HttpStatus.OK);
		
	}
	
	@PostMapping(value="/delete")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<CategoryDTO> deleteCategory(@RequestBody CategoryDTO category){
		CategoryThresholds c = new CategoryThresholds();
		c.setId(category.getId());
		c.setCategory(category.getCategory());
		c.setDeleted(true);
		c.setDiscount(category.getDiscount());
		c.setThreshold(category.getThreshold());
		boolean deleted = false;
		try {
			deleted = thresholdService.delete(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(deleted) {
			return new ResponseEntity<>(new CategoryDTO(c), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}
}
