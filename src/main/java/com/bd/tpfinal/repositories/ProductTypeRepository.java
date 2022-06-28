package com.bd.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bd.tpfinal.model.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>{
	
	
	
}
