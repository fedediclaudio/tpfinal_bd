package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.interfaces.IProductTypeRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long>, IProductTypeRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	ProductType getProductTypeById(Long id);
	
}
