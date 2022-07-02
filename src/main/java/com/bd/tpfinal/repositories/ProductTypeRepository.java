package com.bd.tpfinal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bd.tpfinal.model.ProductType;
import org.bson.types.ObjectId;

@Repository
public interface ProductTypeRepository extends MongoRepository<ProductType, ObjectId>{
	
	
	
}
