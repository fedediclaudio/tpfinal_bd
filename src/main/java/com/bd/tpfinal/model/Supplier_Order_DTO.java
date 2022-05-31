package com.bd.tpfinal.model;

import java.util.List;

public class Supplier_Order_DTO
{
    private Long id_supplier;
    private Long cantidad_ordenes;

    public Supplier_Order_DTO()
    {
    }

    public Supplier_Order_DTO(Long id_supplier, Long cantidad_ordenes)
    {
        this.id_supplier = id_supplier;
        this.cantidad_ordenes = cantidad_ordenes;
    }

    public Long getId_supplier()
    {
        return id_supplier;
    }

    public void setId_supplier(Long id_supplier)
    {
        this.id_supplier = id_supplier;
    }

    public Long getCantidad_ordenes()
    {
        return cantidad_ordenes;
    }

    public void setCantidad_ordenes(Long cantidad_ordenes)
    {
        this.cantidad_ordenes = cantidad_ordenes;
    }
}