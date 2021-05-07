package com.mrsisa.mrsisaprojekat.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Order;

public interface OrderRepositoryDB extends JpaRepository<Order, Long>{

	@Query("select o from Order o join fetch o.medicamentItems join fetch o.admin where o.deadline > CURRENT_DATE")
	Set<Order> getOrdersForSupplier();
}
