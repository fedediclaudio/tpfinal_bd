package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
