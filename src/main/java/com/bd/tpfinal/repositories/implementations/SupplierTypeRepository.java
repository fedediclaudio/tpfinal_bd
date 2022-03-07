package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.interfaces.ISupplierTypeRepository;

public interface SupplierTypeRepository extends JpaRepository<SupplierType, Long>, ISupplierTypeRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	SupplierType getSupplierTypeById(long id);
	SupplierType getSupplierTypeByName(String name);
	
}
