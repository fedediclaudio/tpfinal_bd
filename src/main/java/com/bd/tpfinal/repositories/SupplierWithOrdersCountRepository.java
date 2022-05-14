package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.SupplierWithOrdersCount;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplierWithOrdersCountRepository extends MongoRepository<SupplierWithOrdersCount, Long> {

}
