package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.interfaces.IProductRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, IProductRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Product getProductById(long id);
	
}
