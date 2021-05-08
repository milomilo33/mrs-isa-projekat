package com.mrsisa.mrsisaprojekat.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.repository.OrderRepositoryDB;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepositoryDB orderRepository;
	
	@Override
	public Set<Order> findAllBeforeDeadline() {
		Set<Order> orders = orderRepository.getOrdersForSupplier();
		if(orders == null) {
			return null;
		}
		return orders;
	}

	@Override
	public Order findOneWithOffers(Long id) {
		return orderRepository.getOrderWithOffers(id);
	}

	@Override
	public Order update(Order orderSaved) {
		return orderRepository.save(orderSaved);
	}

	@Override
	public Set<Order> findAllOrders() {
		Set<Order> orders = orderRepository.getOrders();
		if(orders == null) {
			return null;
		}
		return orders;
	}

	@Override
	public Order create(Order o) {
		return orderRepository.save(o);
	}

	@Override
	public Order findOneWithOffersAndAdmin(Long id) {
		Order o = orderRepository.getOrderWithAdmin(id);
		if(o == null) {
			return null;
		}
		return o;
	}

	@Override
	public boolean delete(Order o) {
		Order order =  orderRepository.getOrderWithAdmin(o.getId());
		if(order != null) {
			order.setDeleted(true);
			orderRepository.save(order);
			return true;
		}
		return false;
		
	}

	@Override
	public Order findOneWithMedicaments(Long id) {
		Order o = orderRepository.getOrderWithMedicaments(id);
		if(o == null) {
			return null;
		}
		return o;
	}

	
}
