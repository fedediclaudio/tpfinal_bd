package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.SupplierType;

public interface SupplierTypeService {
	
	SupplierType createNewSupplierType(SupplierType supplierType) throws Exception;
	List<SupplierType> getSupplierTypeList() throws Exception;
	
}
