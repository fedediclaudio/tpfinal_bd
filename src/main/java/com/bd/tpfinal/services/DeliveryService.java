package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.Collection;

import org.bson.types.ObjectId;

import com.bd.tpfinal.dto.DeliveryManDTO;
import com.bd.tpfinal.dto.OrderDTO;
import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Qualification;

public interface DeliveryService {

			
	//For Order
		
	OrderDTO createOrderPending(Order order,ObjectId supplierId);

	Order readOrder(ObjectId number);

	Collection<Order> getAssignedOrders(String username); 

	boolean assignOrder(ObjectId number);

	void acceptOrder(ObjectId number);

	void refuseOrder(ObjectId number);

	void cancelOrder(ObjectId number);

	void finishOrder(ObjectId number);
	
	OrderDTO addItem(ObjectId orderId, Item item, ObjectId idSupplier);
	
	void addQualification(ObjectId orderId, Qualification qualification);
	
	OrderDTO getMayorPrecioTotalForDay(LocalDate fecha);

	
	//For Client
	
	Client createClient(Client client);
	
	Client readClient(String username);

	Client updateClient(String username, Client client);

	void desactiveClient(String username);

	Collection<Order> getClientOrders(String username);
	
	Client addAddress(Address address, String username);

	
	//For DeliveryMan
	
	DeliveryMan createDeliveryMan(DeliveryMan deliveryMan);
	
	DeliveryMan readDeliveryMan(String username);

	DeliveryMan updateDeliveryMan(String username, DeliveryMan deliveryMan);

	void desactiveDeliveryMan(String username);

	Collection<DeliveryManDTO> getDiezMayorScore();

}
