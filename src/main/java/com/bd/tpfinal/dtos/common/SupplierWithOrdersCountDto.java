package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SupplierWithOrdersCountDto extends SupplierDto{
    private int ordersCount;
    public SupplierWithOrdersCountDto(String supplierId, String name, String cuil, String supplierType, int ordersCount) {
        super(supplierId, name, cuil, supplierType);
        this.ordersCount = ordersCount;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }
}
