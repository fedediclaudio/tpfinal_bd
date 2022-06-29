package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.FinishOrderScore;
import com.bd.tpfinal.DTOs.ItemDTO;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Item> agregarItemAOrdenCreada(String OrderId, ItemDTO item) throws Exception;

    List<Order> getOrdersConMasProductosDeSupplier(long supplier_id);

    Optional<Order> getOrderConMayorPrecioDelDia(LocalDate fecha);

    Optional<Order> confirmarPedido(String orderId) throws Exception;

    Optional<Order> cancelarPedido(String orderId) throws Exception;

    Optional<Order> rechazarPedido(String orderId) throws Exception;

    Optional<Order> entregarPedido(String orderId) throws Exception;

    Optional<Order> finalizarPedido(FinishOrderScore score, String orderId) throws Exception;

    List<Order> getOrdenesConMasProductosDelSupplier(long supplier_id);

    Optional<Order> getOrderConMayorPrecioTotalDelDia(LocalDate fecha);

    void updateOrdersTotalPrice(String status);
}