package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.ProductType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductTypeRepository extends MongoRepository<ProductType, String> {

    ProductType getProductTypeByName(String name);
}
