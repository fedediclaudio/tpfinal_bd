package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.ProductType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProductTypeRepository extends MongoRepository<ProductType, String> {
    @Query(value = "{'name': ?0}")
    Optional<ProductType> findProductTypesByNameIgnoreCase(String name);

    Optional<ProductType> findProductTypeById(String anId);

    Optional<ProductType> findOneProductTypesByNameIgnoreCase(String name);

}
