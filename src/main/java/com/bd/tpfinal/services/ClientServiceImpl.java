package com.bd.tpfinal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;

@Service
public class ClientServiceImpl implements ClientService {

	public Client addNewClient(Client client) throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public Address addNewAddress(Address address) throws Exception {
		// Lo hace Pablo
				return null;
	}

	public long clientCount() throws Exception {
		// Lo hace Pablo
		return 0;
	}
	
	public List<Client> getAllClients() throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public List<Address> getAddresses(String idClient) throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public List<Order> getAllOrders(String idClient) throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public List<Order> getAllPendingOrders(String idClient) throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public Order getNextPendingOrder(String idClient) throws Exception {
		// Lo hace Pablo
		return null;
	}
	
	public boolean cancelPendingOrder(String idClient, String orderNumber) throws Exception {
		// Lo hace Pablo
		return false;
	}
	
}
