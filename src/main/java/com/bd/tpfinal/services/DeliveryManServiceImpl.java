package com.bd.tpfinal.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.implementations.DeliveryManRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;

public class DeliveryManServiceImpl implements DeliveryManService {
	@Autowired DeliveryManRepository deliveryManRepository;
	@Autowired OrderRepository orderRepository;	
	
	public List<DeliveryMan> getAllDeliveryMan() throws Exception {
		// Retorno todos los DeliveryMan
		return deliveryManRepository.findAll();
	}
	
	public List<Order> getAllPendingOrders(long idDeliveryMan) throws Exception {
		// Retorno todas las ordenes pendientes del DeliveryMan
		return orderRepository.getAllPendingOrders(idDeliveryMan);
	}
	
	public Order getNextPendingOrder(long idDeliveryMan) throws Exception {
		// Retorno la siguiente orden pendiente del DeliveryMan
		return orderRepository.getNextPendingOrder(idDeliveryMan);
	}
	
	@Transactional
	public boolean deliverNextPendingOrder(long idDeliveryMan) throws Exception {
		// Obtengo el DeliveryMan
		DeliveryMan dm = deliveryManRepository.getDeliveryManById( idDeliveryMan );
		// Verifico que este activo y libre, retorno false en caso contrario
		if ((!dm.isActive()) || (!dm.isFree())) return false;
 		
		// Obtengo la siguiente orden pendiente
		Order order = orderRepository.getNextPendingOrder(idDeliveryMan);
		// Verifico si se puede despachar, retorno false en caso contrario
		if (!order.getStatus().canDeliver()) return false;
		
		// Despacho la orden
		order.getStatus().deliver();
		
		// Guardo la orden
		orderRepository.save(order);
		
		return true;
	}

	@Transactional
	public boolean refuseNextPendingOrder(long idDeliveryMan) throws Exception {
		// Obtengo el DeliveryMan
		DeliveryMan dm = deliveryManRepository.getDeliveryManById( idDeliveryMan );
		// Verifico que este activo y libre, retorno false en caso contrario
		if ((!dm.isActive()) || (!dm.isFree())) return false;
 		
		// Obtengo la siguiente orden pendiente
		Order order = orderRepository.getNextPendingOrder(idDeliveryMan);
		// Verifico si se puede cancelar, retorno false en caso contrario
		if (!order.getStatus().canRefuse()) return false;
				
		// Deniego la orden
		order.getStatus().refuse();
		
		// Guardo la orden
		orderRepository.save(order);
		
		return true;
	}
	
	@Transactional
	public boolean finishActualOrder(long idDeliveryMan) throws Exception {
		// Obtengo el DeliveryMan
		DeliveryMan dm = deliveryManRepository.getDeliveryManById( idDeliveryMan );
		// Verifico que este activo, retorno false en caso contrario
		if (!dm.isActive()) return false;
		
		// Obtengo la siguiente orden pendiente
		Order order = orderRepository.getNextPendingOrder(idDeliveryMan);
		// Verifico si se puede terminar, retorno false en caso contrario
		if (!order.getStatus().canFinish()) return false;
		
		// Termino la orden
		order.getStatus().finish();
		
		// Guardo la orden
		orderRepository.save(order);
		
		// Quito la orden, de la lista de ordenes pendientes del DeliveryMan
		dm.removePendingOrder(order);
		// Guardo al DeliveryMan
		deliveryManRepository.save(dm);
		
		return true;
	}
}
