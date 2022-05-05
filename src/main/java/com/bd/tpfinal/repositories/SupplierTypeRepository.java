package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierTypeRepository extends MongoRepository<SupplierType, String> {
}
