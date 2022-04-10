package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("PENDING")
public class Pending extends OrderStatus{

    @Override
    public void setName(String name) {
        super.setName("PENDING");
    }
}
