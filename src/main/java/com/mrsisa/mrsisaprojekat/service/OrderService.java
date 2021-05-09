package com.mrsisa.mrsisaprojekat.service;

import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.model.OrderStatus;

public interface OrderService {

	Set<Order> findAllBeforeDeadline();
	
	Order findOneWithOffers(Long id);

	Order update(Order orderSaved);
	
	Set<Order> findAllOrders();
	
	Order create(Order o);
	
	Order findOneWithOffersAndAdmin(Long id);
	
	boolean delete(Order o);
	
	Order findOneWithMedicaments(Long id);
	
	Set<Order> filterOrders(OrderStatus status);
	
	
}
