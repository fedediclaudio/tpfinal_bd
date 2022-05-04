package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;

public interface OrderRepository extends JpaRepository<Order, Integer>, IOrderRepository {
	
	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Order getOrderByNumber(int number);
	
	@Query(value = "SELECT o.* FROM user_order o, "
			+ "(SELECT id_order, SUM(quantity) AS cant "
			+ "		FROM item  i "
			+ "		INNER JOIN product p on p.id_product = i.id_product "
			+ "		WHERE p.id_supplier = ?1 "
			+ "		GROUP BY id_order "
			+ ") sq "
			+ "WHERE o.`number` = sq.id_order "
			+ "ORDER BY cant DESC "
			+ "LIMIT 10;", 
		  nativeQuery = true)
	List<Order> getOrdersFromSupplier(long idSupplier);
}
