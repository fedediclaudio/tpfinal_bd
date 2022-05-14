package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService
{
    public Order newOrder(Order newOrder);
    public List<Order> getAll();
    public Optional<Order> getById(Long id);
    public Order getByNumber(Long number);
    public boolean assignOrderToDeliveryMan(Long orden, Long dm);
    public Order actualizarOrder(Order orden);
    public void cancelarOrder(Long number) throws Exception;
    //todas las Order de un Supplier
    public List<Order> getOrderByIdSupplier(Long id);
    public double getQualificationAverage(Long id_Supplier);
    public List<Order> getByClientId(Long id);
    public List<Order> getBySupplierMaxCantItems(Long id_supplier);
}
