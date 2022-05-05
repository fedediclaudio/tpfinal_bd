package com.bd.tpfinal.model;

import org.springframework.data.mongodb.core.mapping.Document;

public class Delivered extends OrderStatus{

    public Delivered() {
        setName("DELIVERED");
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
