package com.bd.tpfinal.dtos.request;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

public class ItemRequestDto {
    private int quantity;
    private String description;
    private String productId;

    public ItemRequestDto(String productId, int quantity) {
        this.quantity = quantity;
        this.productId = productId;
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
