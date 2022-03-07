package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.repositories.interfaces.IOrderStatusRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>, IOrderStatusRepository {
	
	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot	
	OrderStatus getOrderStatusById(long id);
	OrderStatus getOrderStatusByName(String name);

}
