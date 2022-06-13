package com.bd.tpfinal.services;

import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    OrderStatusRepository orderStatusRepository;


    public OrderStatus createOrderStatus(OrderStatus orderStatus) throws Exception {
        if (orderStatus.getName().isBlank() || orderStatus.getStartDate() == null) return null;

        return orderStatusRepository.save(orderStatus);
    }

    public OrderStatus getOrderStatus(String name) throws Exception {
        return orderStatusRepository.getOrderStatusByName(name);
    }

}
