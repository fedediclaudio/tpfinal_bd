package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
public class DeliveryMan extends User{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private int numberOfSuccessOrders;

    private boolean free;

    private Date dateOfAdmission;

    @OneToMany( mappedBy = "deliveryMan", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
      private List<Order> ordersPending;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Order> getOrdersPending() {
        return ordersPending;
    }

    public void setOrdersPending(List<Order> ordersPending) {
        this.ordersPending = ordersPending;
    }
}
