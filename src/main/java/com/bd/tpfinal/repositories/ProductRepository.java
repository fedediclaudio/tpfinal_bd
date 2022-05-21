package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findBySupplier_idAndActive(String supplierId, boolean active);
    List<Product> findAllByActive(boolean active);

    Optional<Product> findByIdAndPrices_startDateGreaterThanOrPrices_finishDateLessThan(@Param("id") String id, @Param("from") Date fromDate, @Param("to") Date toDate);
}
