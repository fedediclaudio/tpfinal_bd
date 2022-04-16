package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Cancel;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Qualification;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import org.apache.commons.lang3.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Date;

public class CancelCommand extends ChangeStatusCommand {

    public CancelCommand(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository, OrderMapper orderMapper) {
        super(orderRepository, deliveryManRepository, orderMapper);
    }

    @Override
    @Transactional
    public OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = getOrder(request);

        if (order.getStatus().canCancel()) {
            Qualification qualification = new Qualification();
            qualification.setOrder(order);
            qualification.setScore(0);

            Cancel cancel = new Cancel();
            cancel.setStartDate(new Date());
            cancel.setOrder(order);

            if (request.getCanceledByClient() != null && request.getCanceledByClient()) {
                cancel.setCancelledByClient(request.getCanceledByClient());
                order.getClient().setScore(order.getClient().getScore() -1);
                if (ObjectUtils.isEmpty(qualification.getCommentary()))
                    qualification.setCommentary("Order cancelled by client.");
            }

            if (order.getDeliveryMan() != null)
                order.getDeliveryMan().getOrdersPending().remove(order);

            order.setStatus(cancel);
            order.setQualification(qualification);

            order = orderRepository.save(order);
        }
        return orderMapper.toOrderDto(order);
    }
}
