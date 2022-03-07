package com.bd.tpfinal.repositories.interfaces;

import java.util.List;

import com.bd.tpfinal.model.Order;

public interface IOrderRepository {
	
	// Los metodos que se vayan a porponer aca, tienen que estar implementados en su implementacion (OrderRepositoryImpl)
	
	List<Order> getAllPendingOrdersForClient(long idClient);
	List<Order> getAllPendingOrdersForDeliveryMan(long idDeliveryMan);
	Order getNextPendingOrderForClient(long idClient);
	Order getNextPendingOrderDeliveryMan(long idDeliveryMan);
	
}
