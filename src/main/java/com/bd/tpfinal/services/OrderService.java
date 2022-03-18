package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService
{
    public void addOrder(Order newOrder);
    public List<Order> getAll();
    public Order getOrder(int number);
}
