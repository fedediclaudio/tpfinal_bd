package com.bd.tpfinal.model;


import org.springframework.data.mongodb.core.mapping.Document;

public class Assigned extends OrderStatus {

    public Assigned() {
        setName("ASSIGNED");
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public boolean canDeliver() {
        return true;
    }

    @Override
    public boolean canRefuse() {
        return true;
    }
}
