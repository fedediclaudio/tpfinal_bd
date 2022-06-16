package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Supplier;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
}
