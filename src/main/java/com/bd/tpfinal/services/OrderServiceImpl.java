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
import com.bd.tpfinal.model.Qualification;
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
	@Autowired OrderStatusService orderStatusService;
	@Autowired ProductRepository productRepository;
	
	@Transactional
	public Client saveClient(Client client) throws Exception {
		return clientRepository.save(client);
	}
	
	public Order getOrder(int orderNumber) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderByNumber( orderNumber );
		// Si la orden no existe, retorno false
		if (order ==  null) {
			System.out.println("La orden no existe");
		}
		return order;
	}
	
	public Order createOrder(long idClient) throws Exception {
		// Obtengo el cliente de la BD
		Client client = clientRepository.getClientById( idClient );
		
		// Si el cliente no existe, retorno null
		if (client ==  null) {
			System.out.println("El cliente no existe");
			return null;
		}
		
		// Verifico que el cliente este activo
		if (!client.isActive()) {
			System.out.println("El cliente no está activo");
			return null;
		}
		
		// Creo la nueva orden
		Order order = new Order( client );
		
		// Grabo la orden
		order = orderRepository.save( order );
		
		// Agrego la orden a la lista de ordenes del Cliente
		client.addOrder(order);
		
		// Guardo al cliente
		saveClient(client);
		
		return order;
	}
	
	@Transactional
	public boolean assignAddressToOrder(int orderNumber, long idAddress) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderByNumber( orderNumber );
		// Si la orden no existe, retorno false
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
		
		// Verifico que se pueda cambiar la direccion (debe estar en Pendiente o Asignada)
		if (!order.getStatus().canChangeAddress()) {
			System.out.println("No se puede cambiar la direccion, la orden debe estar en Pendiente o Asignada");
			return false;
		}
		
		// Obtengo la direccion de la BD
		Address address = addressRepository.getAddressById( idAddress ); 
		if (address == null) {
			System.out.println("La direccion no existe");
			return false;
		}
		
		if (!order.getClient().hasAddress(address)) {
			System.out.println("La direccion no le pertenece a usuario");
			return false;
		}
		
		// Actualizo la direccion en la orden
		order.setAddress( address );
		
		// Grabo la orden
		order = orderRepository.save( order );
		
		return true;
	}
	
	@Transactional
	public boolean addProductToOrder(int orderNumber, long idProduct, int quantity, String description) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderByNumber( orderNumber );
		// Si la orden no existe, retorno false
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
		
		// Verifico si el estado actual de la orden permite agregar items
		if (!order.getStatus().canAddItem()) {
			System.out.println("No se puede agregar items a la orden");
			return false;
		}
		
		// Obtengo el producto de la BD
		Product product = productRepository.getProductById( idProduct );
		// Si el producto no existe, retorno false
		if (product == null) {
			System.out.println("El producto no existe");
			return false;
		}
		if (product.isProductDeleted()) {
			System.out.println("El producto ya no esta a la disponible para la venta");
			return false;
		}
		
		// Creo el nuevo item
		Item item = new Item();
		item.setProduct( product );
		item.setQuantity( quantity );
		item.setDescription( description );
		item.setOrder( order );
		
		// Sumo el precio de los productos 
		float price = order.getTotalPrice();
		price += product.getPrice() * quantity;
		order.setTotalPrice(price);
		
		// Agrego el item a la orden
		order.addItem( item );
		
		// Grabo la orden
		orderRepository.save( order );
		
		return true;
	}

	@Transactional
	public boolean cancel(int orderNumber) throws Exception {
		// Obtengo la orden de la BD		
		Order order = orderRepository.getOrderByNumber( orderNumber );
		
		// Si la orden no existe, retorno false
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
		
		// Si no se puede cancelar, retorno falso
		if (!order.getStatus().canCancel()) {
			System.out.println("No se puede cancelar la orden");
			return false;
		}
		
		// Intento cancelar la orden
		if (!order.getStatus().cancel()) {
			System.out.println("No se pudo cancelar la orden");	
			return false;
		}
		
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
	public boolean confirmOrder(int orderNumber) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderByNumber( orderNumber );
		
		// Si la orden no existe, retorno false
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
				
		// Si la orden no se puede aignar, retorno en falso
		if (!order.getStatus().canAssign()) {
			System.out.println("La orden no se puede asignar");
			return false;
		}
		
		// Obtengo la lista de DeliveryMan libres de la BD
		List<DeliveryMan> dmList = deliveryManRepository.getFreeDeliveryManList();
		
		// Si no hay ningun DeliveryMan libre, obtengo todos los DeliveryMan
		if (dmList.isEmpty())
			dmList = deliveryManRepository.findAll();
		
		// Elijo uno de los DeliveryMan de la lista como el repartidor de la orden
		DeliveryMan deliveryMan = chooseDeliveryMan(dmList);
		
		// Asigna el deliveryMan a la orden
		if (!order.getStatus().assign(deliveryMan)) {
			System.out.println("La orden no se pudo asignar");
			return false;
		}
		
		// Grabo la ordn
		orderRepository.save( order );
		
		return true;
	}
	
	@Transactional
	public boolean setQualification(int orderNumber, int score, String comment) throws Exception {
		// Obtengo la orden de la BD
		Order order = orderRepository.getOrderByNumber( orderNumber );
		
		// Si la orden no existe, retorno false
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
				
		// Si la orden no se puede calificar, retorno en falso
		if (!order.getStatus().canRate()) {
			System.out.println("La orden no se puede calificar");
			return false;
		}
		
		if (order.getQualification() != null) {
			System.out.println("La orden ya fue calificada");
			return false;	
		}
		
		if ((score < 0) || (score > 5)) {
			System.out.println("La calificacion debe ser entre 0 y 5");
			return false;	
		}
		
		Qualification qualification = new Qualification();
		qualification.setCommentary(comment);
		qualification.setOrder(order);
		qualification.setScore(score);
		
		order.addQualification(qualification);
		
		// Grabo la ordn
		orderRepository.save( order );
		
		return true;		
	}
}
