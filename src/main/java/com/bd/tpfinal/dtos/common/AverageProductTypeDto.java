package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AverageProductTypeDto {
    @JsonProperty("product_type_id")
    private String productTypeId;
    @JsonProperty("product_type_name")
    private String productTypeName;
    @JsonProperty("average_price")
    private float averagePrice;

}
