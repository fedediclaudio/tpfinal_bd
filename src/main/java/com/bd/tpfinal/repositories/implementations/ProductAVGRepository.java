package com.bd.tpfinal.repositories.implementations;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.dto.ProductAvgDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductAVGRepository extends MongoRepository<Product, String> {

    @Aggregation(pipeline = {
            "{ $unwind: $prices }",
            "{ $group : { '_id': '$name', average : {  $avg : '$prices.price' } } }"})
    List<ProductAvgDTO> avg3();
}
