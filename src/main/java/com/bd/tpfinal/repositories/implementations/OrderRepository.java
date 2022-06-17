package com.bd.tpfinal.repositories.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.projections.OrderMaxPrice;
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;

public interface OrderRepository extends MongoRepository<Order, String>, IOrderRepository {

    // Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot

    Order findByNumber(@Param("id") String id);
    Order findByItems_Id(@Param("id") String id);
    Order findByItems_Product_Id(@Param("id") String id);
    Order getOrderByNumber(String number);

    OrderMaxPrice findTopByDateOfOrderOrderByTotalPriceDesc(LocalDate orderDate);
    
    List<Order> findByItems_Product_id(@Param("product_id") String productId);

    List<Order> findByItems_Product_Supplier_Id(@Param("id") String id);
  
    List<Order> findByQualification_ScoreAndItems_Product_Supplier_Id(float score, String id);

}
