package com.bd.tpfinal.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class SupplierWithOrdersCount extends Supplier {
    @Transient
    private Long ordersCount;

    public SupplierWithOrdersCount() {
    }

    public SupplierWithOrdersCount(Long id, String name, String cuil, String address, float[] coords, float qualificationOfUsers, SupplierType type, Long ordersCount) {
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
