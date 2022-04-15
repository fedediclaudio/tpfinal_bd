package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("PENDING")
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
