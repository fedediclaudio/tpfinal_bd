package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.Supplier;

public interface SupplierRepository extends MongoRepository<Supplier, String> {

	Supplier getSupplierById(String id);
	
	List<Supplier> findByQualificationOfUsersGreaterThanEqual(float qualification);
}
