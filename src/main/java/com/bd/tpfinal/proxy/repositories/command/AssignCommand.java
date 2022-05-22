package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Assigned;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;


import java.util.Date;

public class AssignCommand extends ChangeStatusCommand {

    public AssignCommand(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository, OrderMapper orderMapper) {
        super(orderRepository, deliveryManRepository, orderMapper);
    }

    @Override

    public OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = getOrder(request);

        if (order.getStatus().canAssign()) {
            Assigned assigned = new Assigned();
            assigned.setOrder(order);
            assigned.setStartDate(new Date());
            order.setStatus(assigned);

            DeliveryMan deliveryMan = deliveryManRepository.findTopByPendingOrderIsNull()
                    .orElseThrow(() -> new PersistenceEntityException("Can't find a free delivery man"));
            deliveryMan.setFree(false);
            deliveryMan.setPendingOrder(order);
            deliveryManRepository.save(deliveryMan);

            order.setDeliveryMan(deliveryMan);
            order = orderRepository.save(order);
        } else
            throw new PersistenceEntityException("Can't change order status. Actual order status is " + order.getStatus().getName());
        return orderMapper.toOrderDto(order);
    }
}
