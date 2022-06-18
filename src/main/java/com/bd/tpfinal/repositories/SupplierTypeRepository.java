package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierTypeRepository extends CrudRepository<SupplierType, String> {
    List<SupplierType> findByNameIgnoreCaseContaining(String supplierTypeName);
}
