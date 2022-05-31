package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Supplier;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRepository extends MongoRepository<Supplier, ObjectId> {


}
