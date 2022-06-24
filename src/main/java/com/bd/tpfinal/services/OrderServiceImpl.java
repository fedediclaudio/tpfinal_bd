package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.FinishOrderScore;
import com.bd.tpfinal.DTOs.ItemDTO;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.ItemRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;


    @Override
    public Optional<Order> findOrderById(long order_id) {
        return Optional.empty();
    }

    @Override
    public Optional<Item> agregarItemAOrdenCreada(Long order_id, ItemDTO item) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<Order> getOrdersConMasProductosDeSupplier(long supplier_id) {
        return null;
    }

    @Override
    public Optional<Order> getOrderConMayorPrecioDelDia(LocalDate fecha) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> confirmarPedido(long order_id) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Order> cancelarPedido(long order_id) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Order> rechazarPedido(long order_id) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Order> entregarPedido(long order_id) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Order> finalizarPedido(FinishOrderScore score, long order_id) throws Exception {
        return Optional.empty();
    }

    @Override
    public void guardarOrder(Order order) {

    }

    @Override
    public List<Order> getOrdenesConMasProductosDelSupplier(long supplier_id) {
        return null;
    }

    @Override
    public Optional<Order> getOrderConMayorPrecioTotalDelDia(LocalDate fecha) {
        return Optional.empty();
    }

    @Override
    public List<Order> getOrderByOrderStatus(String status) {
        return null;
    }

    @Override
    public void updateOrdersTotalPrice(String status) {
        List<Order> orderList = new ArrayList<Order>();
        Iterable<Order> orders = orderRepository.findAll();
        orders.forEach(orderList::add);
        int i = 0;
        while (!orderList.isEmpty()) {
            Order order = orderList.get(i);
            if (!order.getStatus().getName().equals("Pending")) {
                int j = 0;
                List <Item> items = itemRepository.findByOrderId(order.getId());
                float total =0;
                while (!items.isEmpty()) {
                    Item it = items.get(j);
                    if (it.getProduct().isActive())
                        total = total + (it.getProduct().getPrice() * it.getQuantity());
                    items.remove(j);
                }
                order.setTotalPrice(total);
                orderRepository.save(order);
                orderList.remove(i);
            }
        }
    }
}
