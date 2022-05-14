package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SupplierDto {

    @JsonProperty("supplier_id")
    private String supplierId;
    private String name;
    private String cuil;
    private float[] coords;
    private String address;
    @JsonProperty("qualification_of_users")
    private float qualificationOfUsers;
    List<ProductDto> products;
    @JsonProperty("supplier_type_id")
    String supplierTypeId;
    @JsonProperty("supplier_type")
    String supplierType;

}
