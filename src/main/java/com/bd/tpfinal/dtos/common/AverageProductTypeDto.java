package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AverageProductTypeDto {
    @JsonProperty("product_type_id")
    private String productTypeId;
    @JsonProperty("product_type_name")
    private String productTypeName;
    @JsonProperty("average_price")
    private float averagePrice;

    public AverageProductTypeDto(String productTypeId, String productTypeName, float averagePrice) {
        this.productTypeId = productTypeId;
        this.productTypeName = productTypeName;
        this.averagePrice = averagePrice;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public float getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(float averagePrice) {
        this.averagePrice = averagePrice;
    }
}
