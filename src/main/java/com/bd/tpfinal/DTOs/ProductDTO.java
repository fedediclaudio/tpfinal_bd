package com.bd.tpfinal.DTOs;

import java.util.List;

public class ProductDTO {
    public String getName() {
        return name;
    }

    public void setName(String nombreProducto) {
        this.name = nombreProducto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcionProducto) {
        this.description = descripcionProducto;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public List<ProductTypeDTO> getType() {
        return type;
    }

    public void setType(List<ProductTypeDTO> types) {
        this.type = types;
    }
    public ProductDTO() {}

    public ProductDTO(
            String nombreProducto,
            String descripcionProducto,
            float price,
            float weight,
            List<ProductTypeDTO> type) {
        this.setName(nombreProducto);
        this.setDescription(descripcionProducto);
        this.setPrice(price);
        this.setWeight(weight);
        this.setType(type);
    }

    private String name;
    private String description;
    private float price;
    private float weight;
    private List<ProductTypeDTO> type;
    private String supplier;
}