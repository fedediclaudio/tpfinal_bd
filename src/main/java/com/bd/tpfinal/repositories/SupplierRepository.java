package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SupplierRepository extends MongoRepository<Supplier, String> {

    @Query(value="{'type.$id': ObjectId(?0) }")
    List<Supplier> findAllByType(String tipoId);

    @Query("{}")
    List<Supplier> getTop10SupplierConMasOrdenesDespachadas();

/*
    @Query(value="{name:{$eq:?0}},{_id:1,name:1,cuil:1}")
    List<Supplier> findByNameIgnoreCaseContaining(String supplierName);

    @Query(value= "")
    List<Supplier> getTop10SupplierConMasOrdenesDespachadas();

    @Query(value = "")
    List<Supplier> getProveedoresWithProductosDeTodosLosTipos();

    Optional<Supplier> findSupplierByCuil(String s);*/
}
