package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
/*
    @Query(value="{name:{$eq:?0}},{_id:1,name:1,cuil:1}")
    List<Supplier> findByNameIgnoreCaseContaining(String supplierName);

    @Query(value= "")
    List<Supplier> getTop10SupplierConMasOrdenesDespachadas();

    List<Supplier> findAllByTypeId(long id_tipo);

    @Query(value = "")
    List<Supplier> getProveedoresWithProductosDeTodosLosTipos();

    Optional<Supplier> findSupplierByCuil(String s);*/
}
