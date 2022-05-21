package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Sent;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;


import java.util.Date;

public class DeliverCommand extends ChangeStatusCommand {

    public DeliverCommand(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository, OrderMapper orderMapper) {
        super(orderRepository, deliveryManRepository, orderMapper);
    }

    @Override

    public OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = getOrder(request);
        final String orderId = order.getId();
        if (order.getStatus().canDeliver()) {
            Sent sent = new Sent();
            sent.setStartDate(new Date());
            sent.setOrder(order);
            order.setStatus(sent);

            DeliveryMan deliveryMan = deliveryManRepository.findById(order.getDeliveryMan().getId())
                    .orElseThrow(() -> new PersistenceEntityException("Error retrieving delivery man for order " + orderId));
            deliveryMan.setNumberOfSuccessOrders(deliveryMan.getNumberOfSuccessOrders()+1);
            deliveryMan.setScore(deliveryMan.getScore()+1);
            deliveryMan = deliveryManRepository.save(deliveryMan);
            order.setDeliveryMan(deliveryMan);

            order = orderRepository.save(order);
        } else
            throw new PersistenceEntityException("Can't change order status. Actual order status is " + order.getStatus().getName());
        return orderMapper.toOrderDto(order);
    }
}
