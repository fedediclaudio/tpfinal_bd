package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;

public interface ClientService {

	Client addNewClient(Client user) throws Exception;
	Address addNewAddress(Address address) throws Exception;
	long clientCount() throws Exception;
	List<Client> getAllClients() throws Exception;
	List<Address> getAddresses(long idClient) throws Exception;
	List<Order> getAllOrders(long idClient) throws Exception;
	List<Order> getAllPendingOrders(long idClient) throws Exception;
	Order getNextPendingOrder(long idClient) throws Exception;
	boolean cancelPendingOrder(long idClient, int orderNumber) throws Exception;
}
