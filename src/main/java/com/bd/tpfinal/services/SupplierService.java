package com.bd.tpfinal.services;

import java.util.List;
import java.util.Map;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.dto.SupplierBadReputation;

public interface SupplierService {
	
	Supplier createNewSupplier(Supplier supplier) throws Exception;
	List<Supplier> getSupplierList() throws Exception;
	List<Supplier> getSupplierListFromType(String idSupplierType) throws Exception;
	Map<Supplier, Integer> getTopTen() throws Exception;
	List<SupplierBadReputation> getSupplierWithAtLeastOneStar() throws Exception;
	List<Supplier> getSupplierWhoOfferAllProducts() throws Exception;
}
