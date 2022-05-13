package com.bd.tpfinal.model;

import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@DiscriminatorValue("DELIVERY_MAN")
public class DeliveryMan extends User{

    private int numberOfSuccessOrders;
    @Transient
    private boolean free = true;
    @Temporal(TemporalType.DATE)
    private Date dateOfAdmission;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Order pendingOrder;

    public int getNumberOfSuccessOrders() {
        return numberOfSuccessOrders;
    }

    public void setNumberOfSuccessOrders(int numberOfSuccessOrders) {
        this.numberOfSuccessOrders = numberOfSuccessOrders;
    }

    public boolean isFree() {
        return pendingOrder == null;
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
