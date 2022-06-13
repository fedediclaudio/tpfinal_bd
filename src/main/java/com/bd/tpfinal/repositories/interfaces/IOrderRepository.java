package com.bd.tpfinal.repositories.interfaces;

import com.bd.tpfinal.model.Order;

import java.util.List;

public interface IOrderRepository {
	
	// Los metodos que se vayan a porponer aca, tienen que estar implementados en su implementacion (OrderRepositoryImpl)
	
	List<Order> getAllOrdersForClient(String idClient, String status);
	List<Order> getAllOrdersForDeliveryMan(String idDeliveryMan, String status);
	Order getNextOrderForClient(String idClient, String status);
	Order getNextOrderDeliveryMan(String idDeliveryMan, String status);
	
}
