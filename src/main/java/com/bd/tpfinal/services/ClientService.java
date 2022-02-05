package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;

public interface ClientService {

	List<Client> getAllClients() throws Exception;
	List<Order> getAllPendingOrders(long idClient) throws Exception;
	Order getNextPendingOrder(long idClient) throws Exception;
	boolean cancelPendingOrder(long idClient, long idOrder) throws Exception;
}
