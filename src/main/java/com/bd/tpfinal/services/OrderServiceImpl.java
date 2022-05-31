package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Optional<Order> findOrderById(long order_id) {
        return orderRepository.findById(order_id);
    }

    @Override
    public Optional<Item> agregarItemAOrdenCreada(Long order_id, Item item) throws Exception{
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order ordenActual = order.get();
            ordenActual.getItems().add(item);
            orderRepository.save(ordenActual);
        } else {
            throw new Exception("La orden con el id: " + order_id + " no existe");
        }
        return Optional.ofNullable(item);
    }

    @Override
    public List<Order> getOrdersConMasProductosDeSupplier(long supplier_id) {
        return orderRepository.getOrdersConMasProductosDeSupplier(supplier_id);
    }

    @Override
    public Optional<Order> getOrderConMayorPrecioDelDia(LocalDate fecha) {
        return orderRepository.getOrdenConMayorPrecioDelDia(fecha);
    }

    @Override
    public void guardarOrder(Order order) {
        orderRepository.save(order);
    }
}
