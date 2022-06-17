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

@Service
public class ClientServiceImpl implements ClientService {
	@Autowired AddressRepository addressRepository;
	@Autowired ClientRepository clientRepository;
	@Autowired OrderRepository orderRepository;

	public Client addNewClient(Client client) throws Exception {
		client.setDateOfRegister( LocalDate.now() );
		client.setActive(true);
		
		// Valido que el cliente sea valido
		if (!client.isValid()) {
			System.out.println("El Cliente no es valido, corrobore los datos enviados");
			return null;
		}
		
		return clientRepository.save( client );
	}
	
	public Address addNewAddress(Address address) throws Exception {
		// Valido que la Address sea valido
		if (!address.isValid()) {
			System.out.println("La Address no es valida, corrobore los datos enviados");
			return null;
		}
		
		// Obtengo el Cliente
		Client client = clientRepository.getClientById( address.getClient().getId() );
		
		// Si el cliente no existe, retorno false
		if (client ==  null) {
			System.out.println("El cliente no existe");
			return null;
		}
		
		// Guardo primero la direccion
		address.setClient(null);
		address = addressRepository.save(address);
		
		// Actualizo el cliente con la nueva direccion
		client.addAddress(address);
		client = clientRepository.save( client );
		
		return client.getAddresses().get( client.getAddresses().size() -1 );
	}

	public long clientCount() throws Exception {
		return clientRepository.count();
	}
	
	public List<Client> getAllClients() throws Exception {
		// Retorno todos los Clientes
		return clientRepository.findAll();
	}
	
	public List<Address> getAddresses(String idClient) throws Exception {
		// Obtengo el Cliente
		Client client = clientRepository.getClientById( idClient );
		
		// Si el cliente no existe, retorno false
		if (client ==  null) {
			System.out.println("El cliente no existe");
			return null;
		}
		
		return client.getAddresses();
	}
	
	public List<Order> getAllOrders(String idClient) throws Exception {
		// Retorno todas las ordenes pendientes del Cliente
		return orderRepository.getAllOrdersForClient(idClient, null);
	}
	
	public List<Order> getAllPendingOrders(String idClient) throws Exception {
		// Retorno todas las ordenes pendientes del Cliente
		return orderRepository.getAllOrdersForClient(idClient, "Pending");
	}
	
	public Order getNextPendingOrder(String idClient) throws Exception {
		// Retorno la siguiente orden pendiente del Cliente
		return orderRepository.getNextOrderForClient(idClient, "Pending");
	}
	
	public boolean cancelPendingOrder(String idClient, String orderNumber) throws Exception {
		// Obtengo el Cliente
		Client client = clientRepository.getClientById( idClient );
		
		// Si el cliente no existe, retorno false
		if (client ==  null) {
			System.out.println("El cliente no existe");
			return false;
		}
		
		// Verifico que este activo, retorno false en caso contrario
		if (!client.isActive()) {
			System.out.println("El cliente no esta activo");
			return false;
		}
 		
		// Obtengo la orden especifica a cancelar
		Order order = orderRepository.getOrderByNumber(orderNumber);
		
		// Si la orden no existe, retorno false
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
				
		// Verifico si se puede cancelar, retorno false en caso contrario
		if (!order.getStatus().canCancel()) {
			System.out.println("La orden no se puede cancelar");
			return false;
		}
		
		// Cancelo la orden
		order.getStatus().cancel();
		
		// Guardo la orden
		orderRepository.save(order);
		
		// Quito la orden de la lista del cliente
		client.removeOrder(order);
		
		// Guardo el cliente
		clientRepository.save(client);
		
		return true;
	}
	
}
