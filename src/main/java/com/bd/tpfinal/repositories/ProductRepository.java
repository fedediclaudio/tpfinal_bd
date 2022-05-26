package com.bd.tpfinal.repositories;

import com.bd.tpfinal.DTOs.ProductDTO;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.bd.tpfinal.model.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    public List<Product> findByNameIgnoreCaseContaining(String name);
    public List<Product> findBySupplierId(long supplier_id);
}
