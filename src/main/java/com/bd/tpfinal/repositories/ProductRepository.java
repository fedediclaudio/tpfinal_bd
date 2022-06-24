package com.bd.tpfinal.repositories;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.Product;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findProductsBySupplierId(String supplier_cuil);

    List<Product> findBySupplierIdAndNameIgnoreCaseContaining(long supplier_id, String name);

    @Aggregation(pipeline = {"{ $group : { '_id': '$name', promedioPrecio : {  $avg : '$price' } } }"})
    List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO();

}
