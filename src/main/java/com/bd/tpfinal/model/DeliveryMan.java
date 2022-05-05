package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Document(collection = "delivery")
public class DeliveryMan extends User {
    @JsonProperty("number_of_success_orders")
    private int numberOfSuccessOrders;

    private boolean free = true;

    @JsonProperty("date_of_admission")
    private Date dateOfAdmission;
    @DBRef(lazy = true)
    @JsonProperty("orders_pending")
    private List<Order> ordersPending = new ArrayList<>();

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

    public void add(Order order){
        this.ordersPending.add(order);
    }
}
