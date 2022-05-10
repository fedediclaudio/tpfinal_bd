package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.interfaces.IProductTypeRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long>, IProductTypeRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	ProductType getProductTypeById(long id);
	ProductType getProductTypeByName(String name);
	
	@Query(value = "SELECT id_product_type, name, description, avg "
			+ "FROM product_type pt, "
			+ "	(SELECT id_type, AVG(price) as avg "
			+ "	FROM product group by id_type) promedio "
			+ "WHERE pt.id_product_type = promedio.id_type;", 
		  nativeQuery = true)
	List<Object[]> getAveragePriceOfProductsByType();
}
