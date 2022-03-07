package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;

public interface OrderRepository extends JpaRepository<Order, Integer>, IOrderRepository {
	
	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Order getOrderByNumber(int number);

}
