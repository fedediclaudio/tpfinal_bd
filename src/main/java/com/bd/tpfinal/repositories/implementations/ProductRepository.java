package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.dto.ProductAvgDTO;
import com.bd.tpfinal.model.projections.ProductAndType;

public interface ProductRepository extends MongoRepository<Product, String>, QuerydslPredicateExecutor<Product> {

    Product getProductById(String id);
    
    List<ProductAndType> findByTypeId(String id);

    List<ProductAndType> findBySupplierId(String id);

    @Aggregation(pipeline = {
            "{ $unwind: $prices }",
            "{ $group : { '_id': '$name', average : {  $avg : '$prices.price' } } }"})
    List<ProductAvgDTO> getAveragePriceOfProducts();
}
