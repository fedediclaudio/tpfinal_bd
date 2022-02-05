package com.bd.tpfinal.repositories.interfaces;

import java.util.List;

import com.bd.tpfinal.model.Order;

public interface IOrderRepository {

	// Los metodos que se vayan a porponerse aca, tienen que estar implementados en su implementacion (OrderRepositoryImpl)
	
	List<Order> getAllPendingOrders(long idUser);
	Order getNextPendingOrder(long idUser);
}
