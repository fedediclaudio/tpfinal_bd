package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierTypeRepository extends MongoRepository <SupplierType, ObjectId> {
}
