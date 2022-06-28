package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.Collection;

import com.bd.tpfinal.dto.OrderDTO;
import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Qualification;

public interface DeliveryService {

			
	//For Order
		
	OrderDTO createOrderPending(Order order,Long supplierId);

	Order readOrder(Long number);

	Collection<Order> getAssignedOrders(String username); 

	boolean assignOrder(Long number);

	void acceptOrder(Long number);

	void refuseOrder(Long number);

	void cancelOrder(Long number);

	void finishOrder(Long number);
	
	OrderDTO addItem(Long orderId, Item item, Long idSupplier);
	
	void addQualification(Long orderId, Qualification qualification);
	
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

	Collection<DeliveryMan> getDiezMayorScore();

}
