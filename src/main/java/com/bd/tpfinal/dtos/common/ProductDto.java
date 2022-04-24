package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDto {
    @JsonProperty("product_id")
    private String productId;
    private boolean active = true;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("supplier_id")
    private String supplierId;
    @JsonProperty("supplier_name")
    private String supplierName;
    @JsonProperty("product_type")
    private String productType;
    @JsonProperty("product_type_id")
    private String productTypeId;
    private float price;
    private float weight;
    @JsonProperty("product_description")
    private String productDescription;

    @Builder
    public ProductDto(String productId, String productName, String productDescription,
                      String supplierId, String supplierName, String productTypeId, String productType, float price, float weight) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.productType = productType;
        this.productTypeId = productTypeId;
        this.price = price;
        this.weight = weight;
    }

}
