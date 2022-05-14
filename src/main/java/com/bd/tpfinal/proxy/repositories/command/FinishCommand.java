package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Delivered;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;

import java.util.Date;

public class FinishCommand extends ChangeStatusCommand {

    public FinishCommand(OrderRepository orderRepository,
                         DeliveryManRepository deliveryManRepository,
                         OrderMapper orderMapper) {
        super(orderRepository, deliveryManRepository, orderMapper);
    }

    @Override
    public OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = getOrder(request);
        if (order.getStatus().canFinish()){

            Delivered delivered = new Delivered();
            delivered.setStartDate(new Date());
            order.setStatus(delivered);
            delivered.setOrder(order);

            DeliveryMan deliveryMan = order.getDeliveryMan();
            deliveryMan.setPendingOrder(null);
            deliveryMan.setScore(deliveryMan.getScore() + 1);
            order.getClient().setScore(order.getClient().getScore() + 1);

            order = orderRepository.save(order);
        } else
            throw new PersistenceEntityException("Can't change order status. Actual order status is "+order.getStatus().getName());
        return orderMapper.toOrderDto(order);
    }
}
