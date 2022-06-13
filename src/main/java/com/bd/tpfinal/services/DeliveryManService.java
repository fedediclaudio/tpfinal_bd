package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;

import java.util.List;

public interface DeliveryManService {

	DeliveryMan addNewDeliveryMan(DeliveryMan deliveryMan) throws Exception;
	long deliveryManCount() throws Exception;
	List<DeliveryMan> getAllDeliveryMan() throws Exception;
	List<Order> getAllPendingOrders(String idDeliveryMan) throws Exception;
	Order getNextPendingOrder(String idDeliveryMan) throws Exception;
	List<DeliveryMan> getTopTen() throws Exception;
	boolean deliverNextPendingOrder(String idDeliveryMan) throws Exception;
	boolean refuseNextPendingOrder(String idDeliveryMan) throws Exception;
	boolean finishActualOrder(String idDeliveryMan) throws Exception;
	
}
