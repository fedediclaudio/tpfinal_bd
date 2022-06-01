package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface OrderService
{
    public void newOrder(Order newOrder);
    public Order newOrder_seteado_state(Order newOrden);
    public List<Order> getAll();
    public Optional<Order> getById(Long id);
    public Order getByNumber(Long number);
    public boolean assignOrderToDeliveryMan(Long orden, Long dm);
    public Order actualizarOrder(Order orden);

    //todas las Order de un Supplier
    public List<Order> getOrderByIdSupplier(Long id);
    public double getQualificationAverage(Long id_Supplier);
    public List<Order> getByClientId(Long id);
    public List<Order> getBySupplierMaxCantItems(Long id_supplier);
    public List<Order> getOrderMaxPricePorFecha(Date fecha);
    public Order getOrderByDateOfOrder(Date date);

    public Order aceptacionDeOrden(Long id_orden) throws Exception;
    public Order finalizacionDeOrden(Long id_orden)  throws Exception;
    public Order rechazoDeOrden(Long id_orden) throws Exception;
    public Order cancelacionDeOrden(Long id_orden) throws Exception;
    public List<Order> getAllWithStatus();
    public List<Order> getAllWithoutQual();
}
