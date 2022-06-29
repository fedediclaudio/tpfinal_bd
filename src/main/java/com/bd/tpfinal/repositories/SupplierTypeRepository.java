package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierTypeRepository extends CrudRepository<SupplierType, String> {
    List<SupplierType> findByNameIgnoreCaseContaining(String supplierTypeName);
}
