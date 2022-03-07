package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.interfaces.ISupplierRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, ISupplierRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Supplier getSupplierById(long id);
	
}
