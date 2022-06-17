package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.DeliveryManRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;

@Service
public class DeliveryManServiceImpl implements DeliveryManService {
	@Autowired ClientRepository clientRepository;
	@Autowired DeliveryManRepository deliveryManRepository;
	@Autowired OrderRepository orderRepository;

	public DeliveryMan addNewDeliveryMan(DeliveryMan deliveryMan) throws Exception {
		deliveryMan.setDateOfAdmission( LocalDate.now() );
		deliveryMan.setActive(true);
		
		// Valido que el DeliveryMan sea valido
		if (!deliveryMan.isValid()) {
			System.out.println("El DeliveryMan no es valido, corrobore los datos enviados");
			return null;
		}
		
		// Hay que hacer todas las validaciones previas al guardado!!
		deliveryMan = deliveryManRepository.save( deliveryMan );
		
		return deliveryMan;
	}
	
	public long deliveryManCount() throws Exception {
		return deliveryManRepository.count();
	}
	
	public List<DeliveryMan> getAllDeliveryMan() throws Exception {
		// Retorno todos los DeliveryMan
		return deliveryManRepository.findAll();
	}
	
	public List<Order> getAllPendingOrders(String idDeliveryMan) throws Exception {
		// Retorno todas las ordenes pendientes del DeliveryMan
		return orderRepository.getAllOrdersForDeliveryMan(idDeliveryMan, "Assigned");
	}
	
	public Order getNextPendingOrder(String idDeliveryMan) throws Exception {
		// Retorno la siguiente orden pendiente del DeliveryMan
		return orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Assigned");
	}

	public List<DeliveryMan> getTopTen() throws Exception {
		return deliveryManRepository.findTop10ByOrderByScoreDesc();
	}
	
	public boolean deliverNextPendingOrder(String idDeliveryMan) throws Exception {
		// Obtengo el DeliveryMan
		DeliveryMan dm = deliveryManRepository.getDeliveryManById( idDeliveryMan );
		
		// Si el DeliveryMan no existe, retorno false
		if (dm ==  null) {
			System.out.println("La DeliveryMan no existe");
			return false;
		}
		
		// Verifico que este activo y libre, retorno false en caso contrario
		if (!dm.isActive()) {
			System.out.println("El DeliveryMan no esta activo");	
			return false;
		}
		if (!dm.isFree()) {
			System.out.println("El DeliveryMan no esta libre");	
			return false;
		}
 		
		// Obtengo la siguiente orden pendiente
		Order order = orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Assigned");
		
		// Verifico que haya alguna Orden por enviar
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
		
		// Verifico si se puede despachar, retorno false en caso contrario
		if (!order.getStatus().canDeliver()) {
			System.out.println("La orden no se puede despachar");
			return false;
		}
		
		// Despacho la orden
		order.getStatus().deliver();
		
		// Guardo la orden
		orderRepository.save(order);
		
		// Guardo el DeliveryMan
		dm.setFree(false);
		deliveryManRepository.save(dm);
		
		return true;
	}
	
	public boolean refuseNextPendingOrder(String idDeliveryMan) throws Exception {
		// Obtengo el DeliveryMan
		DeliveryMan dm = deliveryManRepository.getDeliveryManById( idDeliveryMan );
		
		// Si el DeliveryMan no existe, retorno false
		if (dm ==  null) {
			System.out.println("La DeliveryMan no existe");
			return false;
		}
				
		// Verifico que este activo y libre, retorno false en caso contrario
		if ((!dm.isActive()) || (!dm.isFree())) {
			System.out.println("El DeliveryMan no esta activo o libre");	
			return false;
		}
 		
		// Obtengo la siguiente orden pendiente
		Order order = orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Assigned");
		
		// Verifico que haya alguna Orden para refutar
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
		
		// Verifico si se puede cancelar, retorno false en caso contrario
		if (!order.getStatus().canRefuse()) {
			System.out.println("La orden no se puede cancelar");
			return false;
		}
				
		// Deniego la orden
		order.getStatus().refuse();
		
		// Guardo la orden
		orderRepository.save(order);
		
		return true;
	}
	
	public boolean finishActualOrder(String idDeliveryMan) throws Exception {
		// Obtengo el DeliveryMan
		DeliveryMan dm = deliveryManRepository.getDeliveryManById( idDeliveryMan );
		
		// Si el DeliveryMan no existe, retorno false
		if (dm ==  null) {
			System.out.println("La DeliveryMan no existe");
			return false;
		}
				
		// Verifico que este activo, retorno false en caso contrario
		if (!dm.isActive()) {
			System.out.println("El DeliveryMan no esta activo");	
			return false;
		}
		
		// Obtengo la siguiente orden Actual
		Order order = orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Sent");
		
		// Verifico que haya alguna Orden para finalizar
		if (order ==  null) {
			System.out.println("La orden no existe");
			return false;
		}
				
		// Verifico si se puede terminar, retorno false en caso contrario
		if (!order.getStatus().canFinish()) {
			System.out.println("La orden no se puede terminar");
			return false;
		}
		
		// Termino la orden
		order.getStatus().finish();
		
		// Guardo la orden
		orderRepository.save(order);
		
		// Actualizo al cliente
		clientRepository.save(order.getClient());
		
		// Actualizo al DeliveryMan
		dm = order.getDeliveryMan();
		// Quito la orden, de la lista de ordenes pendientes del DeliveryMan
		dm.removePendingOrder(order);
		
		// Guardo al DeliveryMan
		deliveryManRepository.save(dm);
		
		return true;
	}
}
