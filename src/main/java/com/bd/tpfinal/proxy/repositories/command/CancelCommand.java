package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.ClientRepository;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import org.apache.commons.lang3.ObjectUtils;


import java.util.Date;

public class CancelCommand extends ChangeStatusCommand {

    private final ClientRepository clientRepository;

    public CancelCommand(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository, ClientRepository clientRepository, OrderMapper orderMapper) {
        super(orderRepository, deliveryManRepository, orderMapper);
        this.clientRepository = clientRepository;
    }

    @Override

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
                if (order.getDeliveryMan() != null) {
                    String id = order.getDeliveryMan().getId();
                    DeliveryMan deliveryMan = deliveryManRepository.findById(id)
                            .orElseThrow(() -> new PersistenceEntityException("Can't find delivery man with id: " + id)) ;
                    deliveryMan.setPendingOrder(null);
                    deliveryManRepository.save(deliveryMan);

                    String clientId = order.getClient().getId();
                    Client client = clientRepository.findById(clientId)
                            .orElseThrow(() -> new PersistenceEntityException("Can't find client with id: "+ clientId));
                    client.setScore(client.getScore() -1);
                    client = clientRepository.save(client);
                    order.setClient(client);
                }

                if (ObjectUtils.isEmpty(qualification.getCommentary()))
                    qualification.setCommentary("Order cancelled by client.");
            }

            order.setStatus(cancel);
            order.setQualification(qualification);

            order = orderRepository.save(order);
        } else
            throw new PersistenceEntityException("Can't change order status. Actual order status is "+order.getStatus().getName());
        return orderMapper.toOrderDto(order);
    }
}
