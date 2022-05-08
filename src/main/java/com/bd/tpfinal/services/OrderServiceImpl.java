package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Optional<Item> AgregarItemAOrdenCreada(Long order_id, Item item) throws Exception{
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
}
