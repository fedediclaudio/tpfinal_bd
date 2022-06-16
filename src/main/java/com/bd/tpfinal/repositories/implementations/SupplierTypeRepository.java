package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.SupplierType;

public interface SupplierTypeRepository extends MongoRepository<SupplierType, String> {

	SupplierType getSupplierTypeById(String id);
	
	SupplierType getSupplierTypeByName(String name);
	
}
