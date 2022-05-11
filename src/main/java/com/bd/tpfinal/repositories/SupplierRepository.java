package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>
{
    List<Supplier> findByName(@Param("name") String name);

    @Query(value= "SELECT DISTINCT sup FROM Supplier sup WHERE sup.supplierType.id = :supplier_type_id")
    List<Supplier> findBySupplierTypeId(@Param("supplier_type_id") Long supplier_type_id);
}
