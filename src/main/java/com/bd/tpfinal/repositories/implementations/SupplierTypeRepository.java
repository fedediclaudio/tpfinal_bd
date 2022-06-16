package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.interfaces.ISupplierTypeRepository;

public interface SupplierTypeRepository extends MongoRepository<SupplierType, String>, ISupplierTypeRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	SupplierType getSupplierTypeById(String id);
	
	SupplierType getSupplierTypeByName(String name);
	
}
