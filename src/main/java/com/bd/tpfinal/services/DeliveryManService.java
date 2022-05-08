package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;

public interface DeliveryManService {

	DeliveryMan addNewDeliveryMan(DeliveryMan deliveryMan) throws Exception;
	long deliveryManCount() throws Exception;
	List<DeliveryMan> getAllDeliveryMan() throws Exception;
	List<Order> getAllPendingOrders(long idDeliveryMan) throws Exception;
	Order getNextPendingOrder(long idDeliveryMan) throws Exception;
	List<DeliveryMan> getTopTen() throws Exception;
	boolean deliverNextPendingOrder(long idDeliveryMan) throws Exception;
	boolean refuseNextPendingOrder(long idDeliveryMan) throws Exception;
	boolean finishActualOrder(long idDeliveryMan) throws Exception;
	
}
