package com.mrsisa.mrsisaprojekat.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.MedicamentItemDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.PricelistItemMedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItem;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;
import com.mrsisa.mrsisaprojekat.service.MedicamentService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;
import com.mrsisa.mrsisaprojekat.service.PriceService;
import com.mrsisa.mrsisaprojekat.service.PricelistItemMedicamentService;

@RestController
@RequestMapping(value = "api/pricelistItems")
public class PricelistItemController {
	
	@Autowired
	private PricelistItemMedicamentService pricelistItemService;
	@Autowired
	private PriceService priceService;
	@Autowired
	private MedicamentService medicamentService;
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PricelistItemMedicamentDTO>> getPriceListItems(@PathVariable("id") Long id){
		
		Collection<PricelistItemMedicament> items = pricelistItemService.findAllPharmacy(id);
		ArrayList<PricelistItemMedicamentDTO> list = new ArrayList<>();
		for(PricelistItemMedicament p : items) {
			list.add(new PricelistItemMedicamentDTO(p));
		}
		
		return new ResponseEntity<Collection<PricelistItemMedicamentDTO>>(list, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<PricelistItemMedicamentDTO> savePricelistItemMedicament(@RequestBody PricelistItemMedicament pricelistItem) throws Exception{
		
		Price price = new Price();
		price.setDeleted(false);
		price.setValue(pricelistItem.getPrice().getValue());
		price.setDateFrom(pricelistItem.getPrice().getDateTo());
		price.setDateTo(null);
		price.setPoints(pricelistItem.getPrice().getPoints());
		
		Price saved = priceService.create(price);
		Medicament medicament = medicamentService.findOne(pricelistItem.getMedicament().getId());
		Pharmacy pharmacy = pharmacyService.findOne(pricelistItem.getPharmacy().getId());
		PricelistItemMedicament p = new PricelistItemMedicament();
		p.setMedicament(medicament);
		p.setPrice(saved);
		p.setPharmacy(pharmacy);
		
		PricelistItemMedicament savedP = pricelistItemService.create(p);
		return new ResponseEntity<>(new PricelistItemMedicamentDTO(savedP), HttpStatus.CREATED); 
	
	}
	
	@PutMapping(value= "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistItemMedicamentDTO> updatePharmacy(@RequestBody PricelistItemMedicament pricelistItem,@PathVariable("id") Long id) throws Exception {
		PricelistItemMedicament pricelistUpdate = pricelistItemService.findOnePricelistItemMedicament(id);
		if (pricelistUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		pricelistItem.setId(id);
		pricelistItem.getPrice().setDateFrom(pricelistItem.getPrice().getDateTo());;
		pricelistUpdate = pricelistItemService.update(pricelistItem);
		return new ResponseEntity<PricelistItemMedicamentDTO>(new PricelistItemMedicamentDTO(pricelistUpdate), HttpStatus.OK);
	}

}
