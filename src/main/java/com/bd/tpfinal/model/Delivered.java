package com.bd.tpfinal.model;

public class Delivered extends OrderStatus{

    public Delivered() {
        setName("DELIVERED");
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
