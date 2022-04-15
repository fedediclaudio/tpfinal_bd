package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
@Entity
@DiscriminatorValue("DELIVERED")
public class Delivered extends OrderStatus{

    public Delivered() {
        setName("DELIVERED");
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
