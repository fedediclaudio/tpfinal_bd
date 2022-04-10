package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
