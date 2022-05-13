package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@DiscriminatorValue("DELIVERY_MAN")
public class DeliveryMan extends User{

    private int numberOfSuccessOrders;

    private boolean free = true;
    @Temporal(TemporalType.DATE)
    private Date dateOfAdmission;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order pendingOrder;

    public int getNumberOfSuccessOrders() {
        return numberOfSuccessOrders;
    }

    public void setNumberOfSuccessOrders(int numberOfSuccessOrders) {
        this.numberOfSuccessOrders = numberOfSuccessOrders;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public Order getPendingOrder() {
        return pendingOrder;
    }

    public void setPendingOrder(Order pendingOrder) {
        this.pendingOrder = pendingOrder;
    }
}
