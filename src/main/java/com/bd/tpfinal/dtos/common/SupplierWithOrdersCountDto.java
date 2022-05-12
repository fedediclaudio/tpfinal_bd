package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SupplierWithOrdersCountDto extends SupplierDto{
    private int ordersCount;

    @Builder
    public SupplierWithOrdersCountDto(String supplierId, String name,
                                      String cuil, float[] coords,  String address,
                                      float qualificationOfUsers,
                                      List<ProductDto> products,
                                      String supplierTypeId,String supplierType, int ordersCount) {
        super(supplierId, name, cuil, coords,  address, qualificationOfUsers, products, supplierTypeId, supplierType);
        this.ordersCount = ordersCount;
    }

}
