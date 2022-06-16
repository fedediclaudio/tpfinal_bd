package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierTypeRepository extends CrudRepository<SupplierType, ObjectId> {
    public List<SupplierType> findByNameIgnoreCaseContaining(String supplierTypeName);
}
