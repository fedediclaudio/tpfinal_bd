package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.implementations.AddressRepository;
import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;

import static java.util.Objects.isNull;

@Service
public class ClientServiceImpl implements ClientService {
	@Autowired AddressRepository addressRepository;
	@Autowired ClientRepository clientRepository;
	@Autowired OrderRepository orderRepository;

	public Client addNewClient(Client client) throws Exception {
		client.setDateOfRegister( LocalDate.now() );
		client.setActive(true);
		
		if (!client.isValid()) {
			System.out.println("El Cliente no es valido, corrobore los datos enviados");
			return null;
		}
		
		return clientRepository.save( client );
	}
	
	public Address addNewAddress(Address address) throws Exception {
		if (!address.isValid()) {
			System.out.println("La Address no es valida, corrobore los datos enviados");
			return null;
		}
		Client client = clientRepository.getClientById( address.getClient().getId() );
		if (client ==  null) {
			System.out.println("El cliente no existe");
			return null;
		}
		address.setClient(null);
		address = addressRepository.save(address);
		
		client.addAddress(address);
		client = clientRepository.save( client );
		
		return client.getAddresses().get( client.getAddresses().size() -1 );
	}

	public long clientCount() throws Exception {
		return clientRepository.count();
	}
	
	public List<Client> getAllClients() throws Exception {
		return clientRepository.findAll();
	}
	
	public List<Address> getAddresses(String idClient) throws Exception {
		Client client = clientRepository.getClientById( idClient );
		
		if (isNull(client)) {
			System.out.println("El cliente no existe");
			return null;
		}
		
		return client.getAddresses();
	}
	
	public List<Order> getAllOrders(String idClient) throws Exception {
		return orderRepository.getAllOrdersForClient(idClient, null);
	}
	
	public List<Order> getAllPendingOrders(String idClient) throws Exception {
		return orderRepository.getAllOrdersForClient(idClient, "Pending");
	}
	
	public Order getNextPendingOrder(String idClient) throws Exception {
		return orderRepository.getNextOrderForClient(idClient, "Pending");
	}
	
	public boolean cancelPendingOrder(String idClient, String orderNumber) throws Exception {
		Client client = clientRepository.getClientById( idClient );
		if (client ==  null) {
			System.out.println("El cliente no existe");
			return false;
		}
		if (!client.isActive()) {
			System.out.println("El cliente no esta activo");
			return false;
		}
		Order order = orderRepository.getOrderByNumber(orderNumber);
		if (isNull(order)) {
			System.out.println("La orden no existe");
			return false;
		}
		if (!order.getStatus().canCancel()) {
			System.out.println("La orden no se puede cancelar");
			return false;
		}
		order.getStatus().cancel();
		orderRepository.save(order);
		
		client.removeOrder(order);
		
		clientRepository.save(client);
		
		return true;
	}
	
}
