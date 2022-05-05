package com.bd.tpfinal.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SupplierWithOrdersCount extends Supplier {
    private Long ordersCount;

    public SupplierWithOrdersCount() {
    }

    public SupplierWithOrdersCount(String id, String name, String cuil, String address, float[] coords, float qualificationOfUsers, SupplierType type, Long ordersCount) {
        super(id, name, cuil, address, coords, qualificationOfUsers, type);
        this.ordersCount = ordersCount;
    }

    public Long getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(Long ordersCount) {
        this.ordersCount = ordersCount;
    }
}
