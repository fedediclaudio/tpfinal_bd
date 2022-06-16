package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;

public interface OrderRepository extends MongoRepository<Order, String>, IOrderRepository {

    // Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot

    List<Order> findByItems_Product_Supplier_Id(@Param("id") String id);
  
    List<Order> findByQualification_ScoreAndItems_Product_Supplier_Id(float score, String id);

}
