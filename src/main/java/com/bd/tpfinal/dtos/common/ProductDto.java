package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDto {
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("supplier_id")
    private String supplierId;
    @JsonProperty("supplier_name")
    private String supplierName;
    @JsonProperty("product_type")
    private String productType;
    private float price;

    public ProductDto(String productId, String supplierId, String supplierName, String productType, float price) {
        this.productId = productId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.productType = productType;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
