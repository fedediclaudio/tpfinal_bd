package com.bd.tpfinal.model;

public class ProductTypeAvgPrice_DTO
{
    private double precio_promedio;
    private Long id_product_type;

    public ProductTypeAvgPrice_DTO()
    {
    }

    public ProductTypeAvgPrice_DTO(Long id_product_type, double precio_promedio)
    {
        this.precio_promedio = precio_promedio;
        this.id_product_type = id_product_type;
    }

    public double getPrecio_promedio()
    {
        return precio_promedio;
    }

    public void setPrecio_promedio(double precio_promedio)
    {
        this.precio_promedio = precio_promedio;
    }

    public Long getId_product_type()
    {
        return id_product_type;
    }

    public void setId_product_type(Long id_product_type)
    {
        this.id_product_type = id_product_type;
    }
}
