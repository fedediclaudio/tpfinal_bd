package com.bd.tpfinal.services;

import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusServiceImpl implements OrderStatusService
{
    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository)
    {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void addOrderStatus(OrderStatus orderStatus)
    {
        this.orderStatusRepository.save(orderStatus);
    }

    @Override
    public List<OrderStatus> getAll()
    {
        return this.orderStatusRepository.findAll();
    }

    @Override
    public Optional<OrderStatus> getById(Long id)
    {
        return this.orderStatusRepository.findById(id);
    }

    @Override
    public OrderStatus getByOrder(Long order_id)
    {
        return this.orderStatusRepository.findByOrder(order_id);
    }
}
