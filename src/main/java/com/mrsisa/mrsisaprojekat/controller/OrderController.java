package com.mrsisa.mrsisaprojekat.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.OrderDTO;
import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.service.OrderService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value= "/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping(value= "/forOffer")
	@PreAuthorize("hasAnyRole('SUPPLIER')")
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
}
