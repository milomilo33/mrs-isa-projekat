package com.mrsisa.mrsisaprojekat.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.PricelistItemAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.PricelistItemMedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;
import com.mrsisa.mrsisaprojekat.service.PriceService;
import com.mrsisa.mrsisaprojekat.service.PricelistItemAppointmentService;
import com.mrsisa.mrsisaprojekat.service.PricelistItemMedicamentService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/pricelistItems")
public class PricelistItemController {


	@Autowired
	private PricelistItemMedicamentService pricelistItemService;
	@Autowired
	private PriceService priceService;
	@Autowired
	private PricelistItemAppointmentService pricelistItemAppointmentService;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PricelistItemMedicamentDTO>> getPriceListItems(@PathVariable("id") Long id) throws Exception{
		
		Set<PricelistItemMedicament> items = (Set<PricelistItemMedicament>) pricelistItemService.findPharmacyForMedicament(id);
		ArrayList<PricelistItemMedicamentDTO> list = new ArrayList<>();
		
		//pricelistItemService.checkPromotions(items);
		// samo aktivne cene (odnosne one kod kojih je deleted = false)
		for(PricelistItemMedicament p :items) {
			ArrayList<Price> prices = new ArrayList<>();
			for(Price pp : p.getPrice()) {
				if(pp.isDeleted()) {
					continue;	
				}
				prices.add(pp);
			}
		
			PricelistItemMedicamentDTO pmdt = new PricelistItemMedicamentDTO(p);
			pmdt.setPrice(prices);
			list.add(pmdt);
		}
		
		return new ResponseEntity<Collection<PricelistItemMedicamentDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/appointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PricelistItemAppointmentDTO>> getPriceListItemAppointments(@PathVariable("id") Long id){
		
		Set<PricelistItemAppointment> items = (Set<PricelistItemAppointment>) pricelistItemAppointmentService.findAllPharmacy(id);
		ArrayList<PricelistItemAppointmentDTO> list = new ArrayList<>();
		
		// samo aktivne cene (odnosne one kod kojih je deleted = false)
		for(PricelistItemAppointment p :items) {
			ArrayList<Price> prices = new ArrayList<>();
			for(Price pp : p.getPrice()) {
				if(pp.isDeleted()) {
					continue;	
				}
				prices.add(pp);
			}
		
			PricelistItemAppointmentDTO pmdt = new PricelistItemAppointmentDTO(p);
			pmdt.setPrice(prices);
			list.add(pmdt);
		}
		
		return new ResponseEntity<Collection<PricelistItemAppointmentDTO>>(list, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<PricelistItemMedicamentDTO> savePricelistItemMedicament(@RequestBody PricelistItemMedicamentDTO pricelistItem) throws Exception{
		
		PricelistItemMedicament savedP = pricelistItemService.savePricelistItemMedicament(pricelistItem);
		return new ResponseEntity<>(new PricelistItemMedicamentDTO(savedP),HttpStatus.CREATED);
	
	}
	
	@PutMapping(value= "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistItemMedicamentDTO> updatePharmacy(@RequestBody PricelistItemMedicamentDTO pricelistItem,@PathVariable("id") Long id) throws Exception {
		PricelistItemMedicament pricelistUpdate = pricelistItemService.findOnePricelistItemMedicament(id);
		if (pricelistUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		pricelistUpdate.setId(id);
		
		for(Price p : pricelistUpdate.getPrice()) {
			if(!p.isDeleted()) {
				p.setDeleted(true);
				p.setDateTo(LocalDate.now());
				priceService.update(p);
				
			}
		}
		Price p = new Price();
		p.setValue(pricelistItem.getPrice().get(0).getValue());
		p.setDateFrom(LocalDate.now());
		p.setDateTo(null);
		p.setDeleted(false);
		p.setPoints(pricelistItem.getPrice().get(0).getPoints());
		Price saved = priceService.create(p);
		Set<Price> pr = pricelistUpdate.getPrice();
		pr.add(saved);
		pricelistUpdate.setPrice(pr);
		pricelistUpdate = pricelistItemService.update(pricelistUpdate);
		return new ResponseEntity<PricelistItemMedicamentDTO>(new PricelistItemMedicamentDTO(pricelistUpdate), HttpStatus.OK);
	}
	
	@PutMapping(value= "/appointments/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistItemAppointmentDTO> updateAppointments(@RequestBody PricelistItemAppointmentDTO pricelistItem,@PathVariable("id") Long id) throws Exception {
		PricelistItemAppointment pricelistUpdate = pricelistItemAppointmentService.findOnePricelistItemAppointment(id);
		if (pricelistUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		pricelistUpdate.setId(id);
		
		for(Price p : pricelistUpdate.getPrice()) {
			if(!p.isDeleted()) {
				p.setDeleted(true);
				p.setDateTo(LocalDate.now());
				priceService.update(p);
				
			}
		}
		Price p = new Price();
		p.setValue(pricelistItem.getPrice().get(0).getValue());
		p.setDateFrom(LocalDate.now());
		p.setDateTo(null);
		p.setDeleted(false);
		p.setPoints(0);
		Price saved = priceService.create(p);
		Set<Price> pr = pricelistUpdate.getPrice();
		pr.add(saved);
		pricelistUpdate.setPrice(pr);
		pricelistUpdate = pricelistItemAppointmentService.update(pricelistUpdate);
		return new ResponseEntity<PricelistItemAppointmentDTO>(new PricelistItemAppointmentDTO(pricelistUpdate), HttpStatus.OK);
	}
	
	@GetMapping(value="/getMedsInPharmacy/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PricelistItemMedicamentDTO>> getMedsInPharmacy(@PathVariable("id") Long id){
		Collection<PricelistItemMedicament> items = pricelistItemService.findAllPharmacy(id);
		if(items == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ArrayList<PricelistItemMedicamentDTO> list = new ArrayList<>();
		for(PricelistItemMedicament p : items) {
			list.add(new PricelistItemMedicamentDTO(p));
		}
		
		
		return new ResponseEntity<Collection<PricelistItemMedicamentDTO>>(list, HttpStatus.OK);
	}
	
	@PutMapping(value= "/promotion/{id}/{pId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistItemMedicamentDTO> makePromotion(@RequestBody PricelistItemMedicamentDTO pricelistItem,@PathVariable("id") Long id, @PathVariable("pId") Long pId) throws Exception {
		
		try {
			PricelistItemMedicament pricelistUpdate = pricelistItemService.makePromotion(id,pId, pricelistItem);
			return new ResponseEntity<PricelistItemMedicamentDTO>(new PricelistItemMedicamentDTO(pricelistUpdate), HttpStatus.OK);
		}catch(PessimisticLockingFailureException e) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
			
		}
		
	}
	
	@Scheduled(cron = "0 * * * * *")
	public void checkPenaults() throws Exception {
		Set<PricelistItemMedicament> items = pricelistItemService.findAllPricelistItems();
		pricelistItemService.checkPromotions(items);
	}
	
	
	@GetMapping(value = "/availablePharmacy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PricelistItemMedicamentDTO>> getPriceListItemsAvailablePharmacy(@PathVariable("id") Long id) throws Exception{
		
		Set<PricelistItemMedicament> items = (Set<PricelistItemMedicament>) pricelistItemService.findAllPricelistItems();
		ArrayList<PricelistItemMedicamentDTO> list = new ArrayList<>();
	
		for(PricelistItemMedicament p :items) {
			if(p.getMedicament().getId() == id) {
				ArrayList<Price> prices = new ArrayList<>();
				for(Price pp : p.getPrice()) {
					if(pp.isDeleted()) {
						continue;	
					}
					prices.add(pp);
				}
				PricelistItemMedicamentDTO pmdt = new PricelistItemMedicamentDTO(p);
				pmdt.setPrice(prices);
				list.add(pmdt);
			}
			
		}
		
		return new ResponseEntity<Collection<PricelistItemMedicamentDTO>>(list, HttpStatus.OK);
	}
}
