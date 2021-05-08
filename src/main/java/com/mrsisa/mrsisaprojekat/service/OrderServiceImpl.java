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

	
}
