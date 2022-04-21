package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierTypeRepository extends JpaRepository<SupplierType, Long>
{
    SupplierType findByName(@Param("name") String name);
}
