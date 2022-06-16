package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.ProductType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
    Optional<ProductType> findProductTypesByNameIgnoreCase(String name);
    Optional<ProductType> findOneProductTypesByNameIgnoreCase(String name);
}
