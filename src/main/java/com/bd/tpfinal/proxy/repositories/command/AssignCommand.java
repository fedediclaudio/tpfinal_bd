package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Assigned;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

public class AssignCommand extends ChangeStatusCommand{

    public AssignCommand(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository, OrderMapper orderMapper) {
        super(orderRepository, deliveryManRepository, orderMapper);
    }

    @Override
    @Transactional
    public OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = getOrder(request);

        if (order.getStatus().canAssign()) {
            Assigned assigned = new Assigned();
            assigned.setOrder(order);
            assigned.setStartDate(new Date());
            order.setStatus(assigned);

            DeliveryMan deliveryMan = deliveryManRepository.findTopByFree(true)
                    .orElseThrow(() -> new PersistenceEntityException("Can't find a free delivery man"));
            deliveryMan.setFree(false);
            deliveryMan.getOrdersPending().add(order);
            order.setDeliveryMan(deliveryMan);

            order = orderRepository.save(order);
        }
        return orderMapper.toOrderDto(order);
    }
}