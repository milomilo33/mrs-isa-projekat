package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;

import com.mrsisa.mrsisaprojekat.model.Supplier;

public interface SupplierService {
	Collection<Supplier> findAll();

	Supplier findOne(String id);

	Supplier create(Supplier supplier) throws Exception;

	Supplier update(Supplier supplier) throws Exception;

	void delete(String id);

}
