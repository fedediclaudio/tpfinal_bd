package com.bd.tpfinal.model;

public class Supplier_Qualif_DTO
{
    private Long id_supplier;
    private long cantidad_1;

    public Supplier_Qualif_DTO()
    {
    }

    public Supplier_Qualif_DTO(Long id_supplier,  int cantidad_1)
    {
        this.id_supplier = id_supplier;

        this.cantidad_1 = cantidad_1;
    }

    public Long getId_supplier()
    {
        return id_supplier;
    }

    public void setId_supplier(Long id_supplier)
    {
        this.id_supplier = id_supplier;
    }




    public long getCantidad_1()
    {
        return cantidad_1;
    }

    public void setCantidad_1(long cantidad_1)
    {
        this.cantidad_1 = cantidad_1;
    }
}
