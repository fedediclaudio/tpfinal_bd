package com.bd.tpfinal.model;


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
