package com.bd.tpfinal.model;

import org.springframework.data.mongodb.core.mapping.Document;

public class Pending extends OrderStatus{
    public Pending() {
        setName("PENDING");
    }
    @Override
    public void setName(String name) {
        super.setName("PENDING");
    }

    @Override
    public boolean canAddItem() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public boolean canAssign() {
        return true;
    }


}
