package com.bd.tpfinal.repositories.interfaces;

import java.util.List;

import com.bd.tpfinal.model.Order;

public interface IOrderRepository {
	
	// Los metodos que se vayan a porponer aca, tienen que estar implementados en su implementacion (OrderRepositoryImpl)
	
	List<Order> getAllOrdersForClient(long idClient, String status);
	List<Order> getAllOrdersForDeliveryMan(long idDeliveryMan, String status);
	Order getNextOrderForClient(long idClient, String status);
	Order getNextOrderDeliveryMan(long idDeliveryMan, String status);
	
}
