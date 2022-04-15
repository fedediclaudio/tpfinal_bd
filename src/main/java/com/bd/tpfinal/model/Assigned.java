package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
@Entity
@DiscriminatorValue("ASSIGNED")
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
