package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {
    @Query(value = "{'type.$id': ObjectId(?0) }")
    List<Supplier> findAllByType(String tipoId);

    @Query("{}")
    List<Supplier> getTop10SupplierConMasOrdenesDespachadas();
}
