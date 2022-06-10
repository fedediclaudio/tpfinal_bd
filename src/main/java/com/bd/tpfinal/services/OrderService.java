package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.FinishOrderScore;
import com.bd.tpfinal.DTOs.ItemDTO;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findOrderById(long order_id);
    Optional<Item> agregarItemAOrdenCreada(Long order_id, ItemDTO item) throws Exception;
    List<Order> getOrdersConMasProductosDeSupplier(long supplier_id);
    Optional<Order> getOrderConMayorPrecioDelDia(LocalDate fecha);
    Optional<Order> confirmarPedido(long order_id) throws Exception;
    Optional<Order> cancelarPedido(long order_id) throws Exception;
    Optional<Order> rechazarPedido(long order_id) throws Exception;
    Optional<Order> entregarPedido(long order_id) throws Exception;
    Optional<Order> finalizarPedido (FinishOrderScore score, long order_id) throws Exception;
    void guardarOrder(Order order);
    List<Order> getOrdenesConMasProductosDelSupplier(long supplier_id);
    Optional<Order> getOrderConMayorPrecioTotalDelDia(LocalDate fecha);
    void removeProductAndItemFromOrderAndUpdatePrice(Item item, Product product);
}
