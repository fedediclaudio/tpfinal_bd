package com.bd.tpfinal.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bd.tpfinal.model.SupplierType;

@Repository
public interface SupplierTypeRepository extends MongoRepository<SupplierType, ObjectId>{

}
