package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends MongoRepository<Supplier, String> {

    Optional<Supplier> findSupplierByCuil(String cuil);

    List<Product> findProductsByCuil(String cuil);
}
