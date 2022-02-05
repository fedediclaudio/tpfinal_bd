package com.bd.tpfinal.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.implementations.AddressRepository;
import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.DeliveryManRepository;
import com.bd.tpfinal.repositories.implementations.ItemRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;
import com.bd.tpfinal.repositories.implementations.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired AddressRepository addressRepository;
	@Autowired ClientRepository clientRepository;
	@Autowired DeliveryManRepository deliveryManRepository;
	@Autowired ItemRepository itemRepository;
	@Autowired OrderRepository orderRepository;
	@Autowired ProductRepository productRepository;
	
	@Transactional
	public Order createOrder(long idClient) throws Exception {
		// Obtengo el cliente de la BD
		Client client = clientRepository.getClientById( idClient );
		
		// Si el cliente no existe, retorno null
		if (client ==  null) return null;
		
		// Verifico que el cliente este activo
		if (!client.isActive()) return null;
		
		// Creo la nueva orden
		Order order = new Order( client );
		
		// Grabo la orden
		//order = orderRepository.save( order );
		
		// Agrego la orden a la lista de ordenes del Cliente
		client.addOrder(order);
		
		// Guardo al cliente
		clientRepository.save(client);
		
		return order;
	}
	
	@Transactional
	public boolean assignAddressToOrder(long idOrder, long idAddress) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderById( idOrder );
		// Si la orden no existe, retorno false
		if (order ==  null) return false;
		
		// Verifico que se pueda cambiar la direccion (debe estar en Pendiente o Asignada)
		if (!order.getStatus().canChangeAddress()) return false;
		
		// Obtengo la direccion de la BD
		Address address = addressRepository.getAddressById( idAddress ); 
		if (address == null) return false;
		
		// Actualizo la direccion en la orden
		order.setAddress( address );
		
		// Grabo la orden
		order = orderRepository.save( order );
		
		return true;
	}
	
	@Transactional
	public boolean addProductToOrder(long idOrder, long idProduct, int quantity, String description) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderById( idOrder );
		// Si la orden no existe, retorno false
		if (order ==  null) return false;
		
		// Verifico si el estado actual de la orden permite agregar items
		if (!order.getStatus().canAddItem()) return false;
		
		// Obtengo el producto de la BD
		Product product = productRepository.getProductById( idProduct );
		// Si el producto no existe, retorno false
		if (product == null) return false;
		
		// Creo el nuevo item
		Item item = new Item();
		item.setProduct( product );
		item.setQuantity( quantity );
		item.setDescription( description );
		item.setOrder( order );
		
		// Agrego el item a la orden
		order.addItem( item );
		
		// Grabo la orden
		orderRepository.save( order );
		
		return true;
	}

	@Transactional
	public boolean cancel(long idOrder) throws Exception {
		// Obtengo la orden de la BD		
		Order order = orderRepository.getOrderById( idOrder );
		
		// Si no se puede cancelar, retorno falso
		if (!order.getStatus().canCancel()) return false;
		
		// Intento cancelar la orden
		if (!order.getStatus().cancel()) return false;
		
		orderRepository.save( order );
		
		return true;
	}
	
	/**
	 * Elije un DeliveryMan, buscando el que tenga menos pedidos en su lista
	 * 
	 * @param deliveryManList
	 * @return un DeliveryMan con la menor cantidad de pedidos en espera
	 */
	private DeliveryMan chooseDeliveryMan(List<DeliveryMan> deliveryManList) {
		return deliveryManList.stream()
				.min( 
						(dm1, dm2) -> dm1.getOrdersPending().size() - dm2.getOrdersPending().size() 
					)
				.get();
	}
	
	@Transactional
	public boolean confirmOrder(long idOrder) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderById( idOrder );
		
		// Si la orden no se puede aignar, retorno en falso
		if (!order.getStatus().canAssign()) return false;
		
		// Obtengo la lista de DeliveryMan libres de la BD
		List<DeliveryMan> dmList = deliveryManRepository.getFreeDeliveryManList();
		
		// Si no hay ningun DeliveryMan libre, obtengo todos los DeliveryMan
		if (dmList.isEmpty())
			dmList = deliveryManRepository.findAll();
		
		// Elijo uno de los DeliveryMan de la lista como el repartidor de la orden
		DeliveryMan deliveryMan = chooseDeliveryMan(dmList);
		
		// Asigna el deliveryMan a la orden
		if (!order.getStatus().assign(deliveryMan)) return false;
		
		// Grabo la ordn
		orderRepository.save( order );
		
		return true;
	}
	
}
