package com.mrsisa.mrsisaprojekat.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.MedicamentItemDTO;
import com.mrsisa.mrsisaprojekat.dto.OrderDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Offer;
import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.model.OrderStatus;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.WorkHour;
import com.mrsisa.mrsisaprojekat.model.WorkHour.Day;
import com.mrsisa.mrsisaprojekat.repository.OrderRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.MedicamentItemService;
import com.mrsisa.mrsisaprojekat.service.MedicamentService;
import com.mrsisa.mrsisaprojekat.service.OfferService;
import com.mrsisa.mrsisaprojekat.service.OrderService;
import com.mrsisa.mrsisaprojekat.service.PharmacyAdminService;
import com.mrsisa.mrsisaprojekat.service.PharmacyService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value= "/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private MedicamentItemService medicamentItemService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	
	@GetMapping(value= "/forOffer")
	ResponseEntity<Set<OrderDTO>> getOrdersForOffer(){
		Set<Order> orders = orderService.findAllBeforeDeadline();
		if(orders == null) {
			return new ResponseEntity<Set<OrderDTO>>(HttpStatus.BAD_REQUEST);
		}
		
		Set<OrderDTO> ordersDTO = new HashSet<OrderDTO>();
		for(Order o : orders) {
			ordersDTO.add(new OrderDTO(o));
		}
		
		return new ResponseEntity<Set<OrderDTO>> (ordersDTO, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<Set<OrderDTO>> getPharmacyOrders(@PathVariable("id") Long id) {
		Set<Order> orders = orderService.findAllOrders();
		Set<OrderDTO> ordersPharmacy = new HashSet<OrderDTO>();
		for(Order o : orders) {
			if(o.getAdmin().getPharmacy().getId() == id) {
				ordersPharmacy.add(new OrderDTO(o));
			}
		}
		return new ResponseEntity<Set<OrderDTO>>(ordersPharmacy,HttpStatus.OK);
	}
	
	public boolean check(Set<MedicamentItem> set2, MedicamentItemDTO m) {
		
		for(MedicamentItem mm : set2) {
				if(mm.getMedicament().getId() == m.getMedicament().getId()) {
					return true;
				}
			}
		
	return false;
	}
	
	@Transactional(readOnly = false)
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO order) throws Exception{
		Order o = new Order();
		// ovde staviti deadline od orderDto
		o.setDeadline(LocalDate.now());
		o.setDeleted(false);
		o.setStatus(OrderStatus.WAITINGFOROFFERS);
		o.setSupplier(null);
		Set<MedicamentItem> meds = new HashSet<MedicamentItem>();
		AdminPharmacy admin = pharmacyAdminService.findOne(order.getAdmin().getEmail());
		Pharmacy pharmacy = pharmacyService.findOneWithMedicaments(admin.getPharmacy().getId());
		Set<MedicamentItem> medsInPharmacy = pharmacy.getMedicamentItems();
		Set<MedicamentItemDTO> newMeds = new HashSet<MedicamentItemDTO>();
		Set<MedicamentItemDTO> oldOnes = new HashSet<MedicamentItemDTO>();
		for(MedicamentItemDTO m : order.getMedicamentItems()) {
				if(check(medsInPharmacy, m)) {
					oldOnes.add(m);
				}else {
					newMeds.add(m);
				}
			
		}
		for(MedicamentItemDTO mi: newMeds) {
			MedicamentItem mitem = new MedicamentItem();
			mitem.setDeleted(false);
			Medicament med = medicamentService.findOne(mi.getMedicament().getId());
			mitem.setMedicament(med);
			mitem.setQuantity(0);
			MedicamentItem s = medicamentItemService.create(mitem);
			medsInPharmacy.add(s);
			pharmacy.setMedicamentItems(medsInPharmacy);
			pharmacyService.update(pharmacy);
			MedicamentItem mitem2 = new MedicamentItem();
			mitem2.setDeleted(false);
			mitem2.setMedicament(med);
			mitem2.setQuantity(mi.getQuantity());
			MedicamentItem saved = medicamentItemService.create(mitem2);
			meds.add(saved);	
		}
		
		
		for(MedicamentItemDTO m : oldOnes) {
			MedicamentItem mi = new MedicamentItem();
			mi.setDeleted(false);
			mi.setQuantity(m.getQuantity());
			Medicament med = medicamentService.findOne(m.getMedicament().getId());
			mi.setMedicament(med);
			MedicamentItem saved = medicamentItemService.create(mi);
			meds.add(saved);
		}
		o.setMedicamentItems(meds);
		o.setOffers(null);
		o.setAdmin(admin);
		Order saved = orderService.create(o);
		return new ResponseEntity<>(new OrderDTO(saved), HttpStatus.CREATED);
		
	}
	
	@Transactional(readOnly = false)
	@DeleteMapping(value = "/{id}/{email}")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<OrderDTO> deleteOrder(@PathVariable("id") Long id, @PathVariable("email") String email) {
		Order o = orderService.findOneWithOffersAndAdmin(id);
		System.out.println(o.getAdmin().getEmail());
		Set<Offer> offers = offerService.offersForOrder(id);
		if(o !=null) {
			try {
				if(offers.size() == 0 && o.getStatus().equals(OrderStatus.WAITINGFOROFFERS) && o.getAdmin().getEmail().equals(email)) {
					orderService.delete(o);
					return new ResponseEntity<>(new OrderDTO(o),HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
				
			}catch (NullPointerException e) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping(value= "/updateOrder/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO order,@PathVariable("email") String email) throws Exception {
		Order o = orderService.findOneWithMedicaments(order.getId());

		Set<Offer> offers = offerService.offersForOrder(order.getId());
		if(o !=null) {
			try {
				if(offers.size() == 0 && o.getStatus().equals(OrderStatus.WAITINGFOROFFERS) && o.getAdmin().getEmail().equals(email)) {
					for(MedicamentItemDTO m : order.getMedicamentItems()) {
						MedicamentItem mi = medicamentItemService.findOne(m.getId());
						mi.setQuantity(m.getQuantity());
						MedicamentItem updated = medicamentItemService.update(mi);	
					}
					o.setDeadline(LocalDate.now());
					o = orderService.update(o);
					return new ResponseEntity<>(HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
				
			}catch (NullPointerException e) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
	}


}
