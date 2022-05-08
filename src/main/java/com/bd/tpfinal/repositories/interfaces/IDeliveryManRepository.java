package com.bd.tpfinal.repositories.interfaces;

import java.util.List;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;

public interface IDeliveryManRepository {
	
	// Los metodos que se vayan a porponer aca, tienen que estar implementados en su implementacion (DeliveryManRepositoryImpl)
	
	List<DeliveryMan> getFreeDeliveryManList();
	List<Order> getAllPendingOrders(long idDeliveryMan);
	Order getNextPendingOrder(long idDeliveryMan);
	List<DeliveryMan> getTopTen();
}
