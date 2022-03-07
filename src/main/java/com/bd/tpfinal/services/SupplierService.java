package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.Supplier;

public interface SupplierService {
	
	Supplier createNewSupplier(Supplier supplier) throws Exception;
	List<Supplier> getSupplierList() throws Exception;
	
}
