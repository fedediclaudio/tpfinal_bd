package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String>, IOrderRepository, QuerydslPredicateExecutor<Order> {

    // Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot

    Order getOrderByNumber(String number);

    List<Order> findByItems_Product_id(@Param("product_id") String productId);

    List<Order> findByQualification_ScoreAndItems_Product_Supplier_Id(float score, String id);

}
