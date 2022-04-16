package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Sent;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.Date;

public class DeliverCommand extends ChangeStatusCommand {

    public DeliverCommand(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository, OrderMapper orderMapper) {
        super(orderRepository, deliveryManRepository, orderMapper);
    }

    @Override
    @Transactional
    public OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = getOrder(request);

        Sent sent = new Sent();
        sent.setStartDate(new Date());
        sent.setOrder(order);
        order.setStatus(sent);

        order = orderRepository.save(order);

        return orderMapper.toOrderDto(order);
    }
}
