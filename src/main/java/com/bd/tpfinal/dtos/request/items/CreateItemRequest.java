package com.bd.tpfinal.dtos.request.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateItemRequest {
    private int quantity;
    private String description;
    @JsonProperty("product_id")
    private String productId;

    public CreateItemRequest(String productId, int quantity) {
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
