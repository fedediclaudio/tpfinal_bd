package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {
//    @Query(nativeQuery = true, value = "select s.*, count(*) cant from products p inner join suppliers s on (s.id=p.supplier_id) " +
//        "where p.type_id in( select id from " +
//        "product_types) group by s.id having cant = :count")
//    List<Supplier> findSuppliersWithAllProductTypes(int count);

//    @Query("From Supplier s where s.qualificationOfUsers >= :qualification")
    List<Supplier> findByQualificationOfUsersGreaterThan(float qualification);

//    @Query("FROM Supplier s JOIN s.products p where s.id = :id")
//    Optional<Supplier> findByIdWithProducts(@Param("id") Long id);


}