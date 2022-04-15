package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;

import javax.persistence.PersistenceException;

public abstract class ChangeStatusCommand {
    protected final OrderRepository orderRepository;
    protected final DeliveryManRepository deliveryManRepository;

    protected final OrderMapper orderMapper;

    public ChangeStatusCommand(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository, OrderMapper orderMapper){
        this.orderRepository = orderRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.orderMapper = orderMapper;
    }

    public abstract OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException;

    protected Order getOrder(ChangeOrderStatusDto request) throws PersistenceEntityException {
        return orderRepository.findById(Long.parseLong(request.getOrderId())).orElseThrow(() -> new PersistenceEntityException("Can't find order with id "+request.getOrderId()));
    }
}
