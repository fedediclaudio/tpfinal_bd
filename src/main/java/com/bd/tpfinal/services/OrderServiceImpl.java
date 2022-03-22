package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrder(Order newOrder)
    {
        this.orderRepository.save(newOrder);
    }

    @Override
    public List<Order> getAll()
    {
        return null;
    }

    @Override
    public Order getOrder(int number)
    {
        return null;
    }
}
