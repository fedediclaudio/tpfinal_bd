package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findOrderById(long order_id);
    Optional<Item> agregarItemAOrdenCreada(Long order_id, Item item) throws Exception;
    List<Order> getOrdersConMasProductosDeSupplier(long supplier_id);
    Optional<Order> getOrderConMayorPrecioDelDia(LocalDate fecha);
    void guardarOrder(Order order);
}
