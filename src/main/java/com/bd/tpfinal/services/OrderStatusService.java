package com.bd.tpfinal.services;

import com.bd.tpfinal.model.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderStatusService
{
    public void addOrderStatus(OrderStatus orderStatus);
    public List<OrderStatus> getAll();
    public Optional<OrderStatus> getById(Long id);
    public OrderStatus getByOrder(Long order_id);
}
