package com.bd.tpfinal.services;

import com.bd.tpfinal.model.SupplierType;

import java.util.List;

public interface SupplierTypeService {
	
	SupplierType createNewSupplierType(SupplierType supplierType) throws Exception;
	List<SupplierType> getSupplierTypeList() throws Exception;
	
}
