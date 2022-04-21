package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService
{
    public Order addOrder(Order newOrder);
    public List<Order> getAll();
    public Optional<Order> getById(Long id);
    public Order getByNumber(int number);
}
