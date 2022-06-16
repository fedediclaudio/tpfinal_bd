package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.dto.ProductAvgDTO;
import com.bd.tpfinal.model.projections.ProductAndType;
import com.bd.tpfinal.repositories.interfaces.IProductRepository;

public interface ProductRepository extends MongoRepository<Product, String>, IProductRepository {
    // Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot

    Product getProductById(String id);
    
    List<ProductAndType> findByTypeId(String id);

    List<ProductAndType> findBySupplierId(String id);

    @Aggregation(pipeline = {
            "{ $unwind: $prices }",
            "{ $group : { '_id': '$name', average : {  $avg : '$prices.price' } } }"})
    List<ProductAvgDTO> getAveragePriceOfProducts();
}
