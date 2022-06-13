package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRepository extends MongoRepository<Supplier, String> {


    Supplier getSupplierById(String id);

    @Query("{ 'type._id': '?0' }")
    List<Supplier> getSupplierListFromType(@Param("id") String id);

//	List<Supplier> findTop10ByOrder();

    List<Supplier> findByQualificationOfUsersGreaterThan(float qualification);
}
