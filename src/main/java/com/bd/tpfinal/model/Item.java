package com.bd.tpfinal.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

public class Item extends PersistentEntity{

    private int quantity;

    private String description;
    @NotNull
    @DBRef(lazy = true)
    private Order order;
    private Product product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
