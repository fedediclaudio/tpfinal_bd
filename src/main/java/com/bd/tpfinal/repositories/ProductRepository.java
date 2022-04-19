package com.bd.tpfinal.repositories;

import java.util.List;

import com.bd.tpfinal.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    public List<Product> findBySupplierId(long aSupplierId);

}