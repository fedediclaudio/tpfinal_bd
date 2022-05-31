package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.ProductType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductTypeRepository extends MongoRepository<ProductType, ObjectId> {


}
