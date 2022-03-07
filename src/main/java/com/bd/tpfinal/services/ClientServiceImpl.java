package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;

@Service
public class ClientServiceImpl implements ClientService {
	@Autowired ClientRepository clientRepository;
	@Autowired OrderRepository orderRepository;
	
	@Transactional
	public Client saveClient(Client client) throws Exception {
		return clientRepository.save( client );
	}

	public Client addNewClient(Client client) throws Exception {
		// Hay que hacer todas las validaciones previas al guardado!!
		client.setDateOfRegister( LocalDate.now() );
		
		client = saveClient( client );
		
		return client;
	}

	public long clientCount() throws Exception {
		return clientRepository.count();
	}
	
	public List<Client> getAllClients() throws Exception {
		// Retorno todos los Clientes
		return clientRepository.findAll();
	}
	
	public List<Order> getAllOrders(long idClient) throws Exception {
		// Retorno todas las ordenes pendientes del Cliente
		return orderRepository.getAllPendingOrdersForClient(idClient);
	}
	
	public List<Order> getAllPendingOrders(long idClient) throws Exception {
		// Retorno todas las ordenes pendientes del Cliente
		return orderRepository.getAllPendingOrdersForClient(idClient);
	}
	
	public Order getNextPendingOrder(long idClient) throws Exception {
		// Retorno la siguiente orden pendiente del Cliente
		return orderRepository.getNextPendingOrderForClient(idClient);
	}
	
	@Transactional
	public boolean cancelPendingOrder(long idClient, int orderNumber) throws Exception {
		// Obtengo el Cliente
		Client client = clientRepository.getClientById( idClient );
		// Verifico que este activo, retorno false en caso contrario
		if (!client.isActive()) return false;
 		
		// Obtengo la orden especifica a cancelar
		Order order = orderRepository.getOrderByNumber(orderNumber);
		// Verifico si se puede cancelar, retorno false en caso contrario
		if (!order.getStatus().canCancel()) return false;
		
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
