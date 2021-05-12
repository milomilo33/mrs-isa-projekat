package com.mrsisa.mrsisaprojekat.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.model.OrderStatus;

public interface OrderRepositoryDB extends JpaRepository<Order, Long>{

	@Query("select o from Order o join fetch o.medicamentItems join fetch o.admin where o.deadline > CURRENT_DATE")
	Set<Order> getOrdersForSupplier();
	
	@Query("select o from Order o join fetch o.offers where o.id = ?1")
	Order getOrderWithOffers(Long id);
	
	
	@Query("select o from Order o join fetch o.medicamentItems join fetch o.admin where o.deleted=false")
	Set<Order> getOrders();
	
	@Query("select o from Order o join fetch o.medicamentItems join fetch o.admin where o.deleted=false and o.status=?1")
	Set<Order> filterOrders(OrderStatus status);
	
	@Query("select o from Order o  join fetch o.admin join fetch o.medicamentItems where o.id = ?1")
	Order getOrderWithAdmin(Long id);
	
	@Query("select o from Order o join fetch o.medicamentItems join fetch o.admin where o.id=?1")
	Order getOrderWithMedicaments(Long id);
	
}
