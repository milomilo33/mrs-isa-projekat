package com.mrsisa.mrsisaprojekat.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.OfferDTO;
import com.mrsisa.mrsisaprojekat.model.Offer;
import com.mrsisa.mrsisaprojekat.model.OfferStatus;
import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.model.OrderStatus;
import com.mrsisa.mrsisaprojekat.model.Supplier;
import com.mrsisa.mrsisaprojekat.service.OfferService;
import com.mrsisa.mrsisaprojekat.service.OrderService;
import com.mrsisa.mrsisaprojekat.service.SupplierService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value= "/api/offers")
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SupplierService supplierService;
	
	@PostMapping(value="/save")
	public ResponseEntity<OfferDTO> saveOffer(@RequestBody OfferDTO offer) {
		
		Supplier supplier = supplierService.findOne(offer.getSupplier());
		if(supplier == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Order order = new Order();
		order.setDeleted(false);
		order.setStatus(OrderStatus.valueOf(offer.getOrder().getStatus()));
		order.setId(offer.getOrder().getId());
		order.setDeadline(offer.getOrder().getDeadline());
		order.setMedicamentItems(null);
		order.setAdmin(null);
		
		
		Offer saveOffer = new Offer();
		saveOffer.setDeadline(offer.getDeadline());
		saveOffer.setSupplier(supplier);
		saveOffer.setOrder(order);
		saveOffer.setStatus(OfferStatus.valueOf(offer.getStatus()));
		saveOffer.setTotalPrice(offer.getTotalPrice());
		
		Offer savedOffer = offerService.save(saveOffer);
		
		Order orderSaved = orderService.findOneWithOffers(order.getId());
		Set<Offer> offers = orderSaved.getOffers();
		if(offers == null) {
			offers = new HashSet<Offer>();
		}
		offers.add(savedOffer);
		orderSaved.setOffers(offers);
		
		orderService.update(orderSaved);
		
		return new ResponseEntity<OfferDTO>(offer, HttpStatus.OK);
	}

}
