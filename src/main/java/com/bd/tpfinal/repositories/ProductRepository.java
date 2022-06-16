package com.bd.tpfinal.repositories;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findBySupplierId(long supplier_id);

    /*@Query(value = "")
    List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO();*/

    List<Product> findBySupplierIdAndNameIgnoreCaseContaining(long supplier_id, String name);
}
