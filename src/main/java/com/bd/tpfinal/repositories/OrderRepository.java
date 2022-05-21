package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByStatus_Name(String status);

    Optional<Order> findOneByNumber(int number);

    Order findTopByDateOfOrderBetweenOrderByTotalPriceDesc(Date from, Date to);

    List<Order> findByItems_Product_Supplier_id(@Param("supplier_id") String supplierId);

    List<Order> findByStatus_nameAndItems_product_supplier(String status, Supplier supplier);

    List<Order> findByQualification_ScoreAndItems_Product_Supplier_Id(float score, String supplier);

    Set<Order> findByStatus_nameAndQualificationIsNotNullAndItems_product_supplier(String delivered, Supplier supplier);

}
