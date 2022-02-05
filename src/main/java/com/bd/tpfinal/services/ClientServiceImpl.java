package com.bd.tpfinal.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;

public class ClientServiceImpl implements ClientService {
	@Autowired ClientRepository clientRepository;
	@Autowired OrderRepository orderRepository;	
	
	public List<Client> getAllClients() throws Exception {
		// Retorno todos los Clientes
		return clientRepository.findAll();
	}
	
	public List<Order> getAllOrders(long idClient) throws Exception {
		// Retorno todas las ordenes pendientes del Cliente
		return orderRepository.getAllPendingOrders(idClient);
	}
	
	public List<Order> getAllPendingOrders(long idClient) throws Exception {
		// Retorno todas las ordenes pendientes del Cliente
		return orderRepository.getAllPendingOrders(idClient);
	}
	
	public Order getNextPendingOrder(long idClient) throws Exception {
		// Retorno la siguiente orden pendiente del Cliente
		return orderRepository.getNextPendingOrder(idClient);
	}
	
	@Transactional
	public boolean cancelPendingOrder(long idClient, long idOrder) throws Exception {
		// Obtengo el Cliente
		Client client = clientRepository.getClientById( idClient );
		// Verifico que este activo, retorno false en caso contrario
		if (!client.isActive()) return false;
 		
		// Obtengo la orden especifica a cancelar
		Order order = orderRepository.getOrderById(idOrder);
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
