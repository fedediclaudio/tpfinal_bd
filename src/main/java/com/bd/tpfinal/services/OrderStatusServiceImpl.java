package com.bd.tpfinal.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.repositories.implementations.OrderStatusRepository;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
	@Autowired OrderStatusRepository orderStatusRepository;
	
	@Transactional
	public OrderStatus saveOrderStatus(OrderStatus orderStatus) throws Exception {
		return orderStatusRepository.save(orderStatus);
	}
	
	public OrderStatus createOrderStatus(OrderStatus orderStatus) throws Exception {
		if (orderStatus.getName().isBlank() || orderStatus.getStartDate() == null) return null;
		
		return saveOrderStatus(orderStatus);
	}
	
	public OrderStatus getOrderStatus(String name) throws Exception {
		return orderStatusRepository.getOrderStatusByName(name);
	}
	
}
