package com.mrsisa.mrsisaprojekat.service;

import java.util.Set;

import com.mrsisa.mrsisaprojekat.model.Order;

public interface OrderService {

	Set<Order> findAllBeforeDeadline();
	
}
