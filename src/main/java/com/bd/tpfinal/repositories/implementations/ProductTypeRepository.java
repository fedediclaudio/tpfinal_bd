package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.ProductType;

public interface ProductTypeRepository extends MongoRepository<ProductType, String> {

    ProductType getProductTypeById(String id);

    ProductType getProductTypeByName(String name);


}
