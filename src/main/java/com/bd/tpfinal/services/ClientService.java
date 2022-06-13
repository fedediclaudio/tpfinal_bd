package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;

import java.util.List;

public interface ClientService {

	Client addNewClient(Client user) throws Exception;
	Address addNewAddress(Address address) throws Exception;
	long clientCount() throws Exception;
	List<Client> getAllClients() throws Exception;
	List<Address> getAddresses(String idClient) throws Exception;
	List<Order> getAllOrders(String idClient) throws Exception;
	List<Order> getAllPendingOrders(String idClient) throws Exception;
	Order getNextPendingOrder(String idClient) throws Exception;
	boolean cancelPendingOrder(String idClient, String orderNumber) throws Exception;
}
