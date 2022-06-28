package com.bd.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bd.tpfinal.model.SupplierType;

@Repository
public interface SupplierTypeRepository extends JpaRepository<SupplierType, Long>{

}
