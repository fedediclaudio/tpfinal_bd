package com.bd.tpfinal.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom{
		
	public Optional<Order> findFirstByDateOfOrderEqualsOrderByTotalPriceDesc(LocalDate fecha); //for getMayorPrecioTotalForDay
	
	@Query ("select o.id from Order o inner join o.items i where i.product = ?1")
	public List<Long> getOrdersWithProduct(Product p); // for deleteProductID
}

