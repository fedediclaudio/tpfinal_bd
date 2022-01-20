package com.bd.tpfinal.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.implementations.OrderRepository;

public class OrderServiceImpl implements OrderService {
	@Autowired OrderRepository orderRepository;
	
	@Transactional
	public boolean cancel(long idOrder) throws Exception {
		
		Order order = orderRepository.getOrderById( idOrder );
		
		if (!order.getStatus().canCancel()) return false;
		
		if (order.getStatus().cancel())
			orderRepository.save( order );
		
		return true;
	}
	
}
