package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.ProductType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProductTypeRepository extends MongoRepository<ProductType, String> {
    @Query(value = "{'name': ?0}")
    Optional<ProductType> findProductTypesByNameIgnoreCase(String name);

    @Query(value="{'_id':ObjectId('62b723d1084d3b4a236bc040')}")
    Optional<ProductType> findProductTypeById(String anId);
    @Query(value = "find({'name':'Almacen'}).limit(1)")
    Optional<ProductType> findOneProductTypesByNameIgnoreCase(String name);

}
