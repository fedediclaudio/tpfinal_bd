package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
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
    @JsonProperty("one_star_qualification_count")
    private float oneStarQualificationCount;
    List<ProductDto> products;
    @JsonProperty("supplier_type_id")
    String supplierTypeId;
    @JsonProperty("supplier_type")
    String supplierType;

    public SupplierDto(String supplierId, String name, String cuil, float[] coords, String address,
                       float qualificationOfUsers, List<ProductDto> products,
                       String supplierTypeId, String supplierType) {
        this.supplierId = supplierId;
        this.name = name;
        this.cuil = cuil;
        this.coords = coords;
        this.address = address;
        this.qualificationOfUsers = qualificationOfUsers;
        this.products = products;
        this.supplierTypeId = supplierTypeId;
        this.supplierType = supplierType;
    }
}
