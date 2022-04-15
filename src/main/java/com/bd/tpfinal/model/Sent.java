package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("SENT")
public class Sent extends OrderStatus{
    public Sent() {
        setName("SENT");
    }
    @Override
    public void setName(String name) {
        super.setName(name);
    }


    @Override
    public boolean canFinish() {
        return true;
    }
}
