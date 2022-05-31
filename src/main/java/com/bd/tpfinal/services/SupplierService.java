package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.Supplier_Order_DTO;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface SupplierService
{
    public void newSupplier(Supplier newSupplier);
    public List<Supplier> getAll();
    public Supplier getSupplierById(Long id);
    public List<Supplier> getSupplierByName(String name);
    public List<Supplier> getSupplierBySupplierTypeId(Long id);
    public List<Supplier> getTop10Supplier();
    public List<Supplier_Order_DTO> getTopTenSupplierWithOrders();

    public List<Order> getOrderBySupplier();//testeo interno
    public List<Supplier> getByQualification1();

    public List<Supplier> getSupplierWithAllTypes();

}
