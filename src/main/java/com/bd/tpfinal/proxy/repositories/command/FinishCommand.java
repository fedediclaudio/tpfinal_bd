package com.bd.tpfinal.proxy.repositories.command;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Delivered;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.ClientRepository;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.SupplierRepository;


import java.util.Date;

public class FinishCommand extends ChangeStatusCommand {

    private final ClientRepository clientRepository;
    private final SupplierRepository supplierRepository;

    public FinishCommand(OrderRepository orderRepository,
                         DeliveryManRepository deliveryManRepository,
                         ClientRepository clientRepository,
                         OrderMapper orderMapper, SupplierRepository supplierRepository) {
        super(orderRepository, deliveryManRepository, orderMapper);
        this.clientRepository = clientRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override

    public OrderDto execute(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = getOrder(request);
        final String orderId = order.getId();
        if (order.getStatus().canFinish()){

            Delivered delivered = new Delivered();
            delivered.setStartDate(new Date());
            order.setStatus(delivered);
            delivered.setOrder(order);

            DeliveryMan deliveryMan = deliveryManRepository.findById(order.getDeliveryMan().getId())
                    .orElseThrow(() -> new PersistenceEntityException("Error retrieving delivery man from order: " + orderId));
            deliveryMan.setPendingOrder(null);
            deliveryMan.setScore(deliveryMan.getScore() + 1);
            deliveryMan = deliveryManRepository.save(deliveryMan);
            order.setDeliveryMan(deliveryMan);

            Client client = clientRepository.findById(order.getClient().getId())
                    .orElseThrow(() -> new PersistenceEntityException("Error retrieving client from order: " + orderId));
            client.setScore(client.getScore() + 1);
            client = clientRepository.save(client);
            order.setClient(client);

            order = orderRepository.save(order);
        } else
            throw new PersistenceEntityException("Can't change order status. Actual order status is "+order.getStatus().getName());
        return orderMapper.toOrderDto(order);
    }
}
