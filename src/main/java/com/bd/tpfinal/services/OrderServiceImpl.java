package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository)
    {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void addOrder(Order newOrder)
    {
        this.orderRepository.save(newOrder);
    }

    @Override
    public List<Order> getAll()
    {
        return this.orderRepository.findAll();
    }

    /**
     * retorna la orden con el atributo status reconstruido.
     * @param id
     * @return
     */
    @Override
    public Optional<Order> getById(Long id)
    {
        Order order_aux;
        Optional<Order>order_aux_optional = this.orderRepository.findById(id);
        order_aux = order_aux_optional.get();
        OrderStatus orderStatus_aux = this.orderStatusRepository.findByOrder(id);
        order_aux.setStatus(orderStatus_aux);
        return Optional.of(order_aux);
    }

    @Override
    public Order getByNumber(int number)
    {
        Order order_aux = this.orderRepository.findByNumber(number);
        OrderStatus orderStatus_aux = this.orderStatusRepository.findByOrder(order_aux.getId());
        order_aux.setStatus(orderStatus_aux);
        return order_aux;
    }
}
