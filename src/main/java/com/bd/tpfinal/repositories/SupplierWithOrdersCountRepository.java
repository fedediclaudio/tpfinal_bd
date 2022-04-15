package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierWithOrdersCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierWithOrdersCountRepository extends JpaRepository<SupplierWithOrdersCount, Long> {
    @Query("SELECT " +
            "new com.bd.tpfinal.model.SupplierWithOrdersCount(s.id, s.name, s.cuil, s.address, s.coords, s.qualificationOfUsers, s.type,  count(o.id) AS counter) " +
            "FROM Order o JOIN o.items i JOIN i.product p JOIN p.supplier s GROUP BY s.id  ORDER BY counter DESC")
    List<SupplierWithOrdersCount> suppliersAtList10Orders();
}
