package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.interfaces.ISupplierRepository;

public interface SupplierRepository extends MongoRepository<Supplier, String>, ISupplierRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Supplier getSupplierById(String id);
	
	List<Supplier> findByQualificationOfUsersGreaterThanEqual(float qualification);
}
