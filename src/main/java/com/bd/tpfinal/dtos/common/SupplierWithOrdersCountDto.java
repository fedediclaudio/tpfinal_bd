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
                                      String cuil, String address,
                                      float qualificationOfUsers,
                                      List<ProductDto> products,
                                      String supplierType, int ordersCount) {
        super(supplierId, name, cuil, address, qualificationOfUsers, products, supplierType);
        this.ordersCount = ordersCount;
    }

}
