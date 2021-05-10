package com.mrsisa.mrsisaprojekat.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.OfferDTO;
import com.mrsisa.mrsisaprojekat.dto.OrderDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Offer;
import com.mrsisa.mrsisaprojekat.model.OfferStatus;
import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.model.OrderStatus;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.Supplier;
import com.mrsisa.mrsisaprojekat.model.WorkHour;
import com.mrsisa.mrsisaprojekat.model.WorkHour.Day;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.MedicamentItemService;
import com.mrsisa.mrsisaprojekat.service.OfferService;
import com.mrsisa.mrsisaprojekat.service.OrderService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;
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
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MedicamentItemService medicamentItemService;
	
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
	
	@GetMapping(value="/{email}")
	@PreAuthorize("hasAnyRole('SUPPLIER')")
	ResponseEntity<Set<OfferDTO>> getSupplierOffers(@PathVariable("email") String email){
		Set<Offer> offers = offerService.supplierOffers(email);
		Set<OfferDTO> offersDTO = new HashSet<OfferDTO>();
		for(Offer o: offers) {
			Order order = orderService.findOneWithMedicaments(o.getOrder().getId());
			o.setOrder(order);
			offersDTO.add(new OfferDTO(o));
		}
		
		return new ResponseEntity<Set<OfferDTO>>(offersDTO, HttpStatus.OK);
	}

	@Transactional(readOnly = false)
	@PutMapping(value= "/updateOffers/{id}/{idOffer}/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<OfferDTO> updateOffers(@RequestBody OfferDTO offerA,@PathVariable("idOffer") Long idOffer,@PathVariable("id") Long id,@PathVariable("email") String email) throws Exception {
		Order order = orderService.findOneWithMedicaments(id);
		//if(LocalDate.now().compareTo(order.getDeadline()) <=0){
		//	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		//}
		Supplier supplier = supplierService.findOne(email);
		Set<Offer> offers = offerService.offersForOrder(id);
		for(Offer of: offers) {
			if(of.getId() != idOffer) {
				Offer rejected = offerService.findOffer(of.getId());
				rejected.setStatus(OfferStatus.REJECTED);
				Offer savedRejected = offerService.update(rejected);
				emailService.sendSupplierMail(supplier, savedRejected);
			}else {
				
				Offer o = offerService.findOffer(idOffer);
				o.setStatus(OfferStatus.ACCEPTED);
				Offer saved = offerService.update(o);
				emailService.sendSupplierMail(supplier, saved);
			}
		}
		
		AdminPharmacy admin =pharmacyAdminService.findOne(order.getAdmin().getEmail());
		Pharmacy pharmacy = pharmacyService.findOneWithMedicaments(admin.getPharmacy().getId());
		for(MedicamentItem mi: pharmacy.getMedicamentItems()) {
			for(MedicamentItem mm: order.getMedicamentItems()) {
				if(mi.getMedicament().getId() == mm.getMedicament().getId()) {
					MedicamentItem medicamentUpdate = medicamentItemService.findOne(mi.getId());
					int q = medicamentUpdate.getQuantity();
					medicamentUpdate.setQuantity(q+mm.getQuantity());
					medicamentUpdate = medicamentItemService.update(medicamentUpdate);
				}
			}
		}
		//proci kroz medicamentItems u apoteci i azurirati
		order.setStatus(OrderStatus.PROCESSED);
		order = orderService.update(order);
		return new ResponseEntity<OfferDTO>( HttpStatus.OK);
	}
	
	@GetMapping(value = "/offersOrder/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<Set<OfferDTO>> getOffersForOrder(@PathVariable("id") Long id) {
		Set<Offer> offers = offerService.offersForOrder(id);
		Set<OfferDTO> offersDTO = new HashSet<OfferDTO>();
		for(Offer o : offers) {
			Offer offer = offerService.findOffer(o.getId());
			OfferDTO od = new OfferDTO();
			od.setDeadline(offer.getDeadline());
			od.setId(offer.getId());
			od.setStatus(offer.getStatus().name());
			od.setSupplier(offer.getSupplier().getEmail());
			od.setTotalPrice(offer.getTotalPrice());
			offersDTO.add(od);
			
		}
		return new ResponseEntity<Set<OfferDTO>>(offersDTO,HttpStatus.OK);
	}
}
