package com.bd.tpfinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("CANCELLED")
public class Cancel extends OrderStatus {

    private boolean cancelledByClient;

    public boolean isCancelledByClient() {
        return cancelledByClient;
    }

    public void setCancelledByClient(boolean cancelledByClient) {
        this.cancelledByClient = cancelledByClient;
    }

    @Override
    public void setName(String name) {
        super.setName("CANCELLED");
    }
}
