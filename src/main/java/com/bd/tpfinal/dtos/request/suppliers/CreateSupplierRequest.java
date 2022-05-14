package com.bd.tpfinal.dtos.request.suppliers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateSupplierRequest {
    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    @JsonProperty("supplier_type_id")
    private String supplierTypeId;

}
