package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierWithOrdersCount;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplierWithOrdersCountRepository extends MongoRepository<SupplierWithOrdersCount, Long> {
//    @Query("SELECT " +
//            "new com.bd.tpfinal.model.SupplierWithOrdersCount(s.id, s.name, s.cuil, s.address, s.coords, s.qualificationOfUsers, s.type,  count(o.id) AS counter) " +
//            "FROM Order o JOIN o.items i JOIN i.product p JOIN p.supplier s GROUP BY s.id  ORDER BY counter DESC")
//    List<SupplierWithOrdersCount> suppliersAtList10Orders();
}
