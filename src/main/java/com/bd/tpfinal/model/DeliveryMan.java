package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Entity
@DiscriminatorValue("DeliveryMan")
public class DeliveryMan extends User{
    private int numberOfSuccessOrders;

    private boolean free;

    private Date dateOfAdmission;
    public DeliveryMan(){}
    public DeliveryMan(String name, String email, String userName, String password, Date dateOfBirth){
    super (name, email,userName, password, dateOfBirth);
    this.numberOfSuccessOrders = 0;
    this.free = true;
    this.dateOfAdmission = Calendar.getInstance().getTime();
    this.ordersPending = new ArrayList<>();

    }

    @OneToMany( mappedBy = "deliveryMan", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
      private List<Order> ordersPending; // las ordenes asignadas al repartidos, en estado assign

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
    public void addOrder(Order order){this.ordersPending.add(order);}
    public void deleteOrder(Order order) { this.ordersPending.remove(order); }
    public void removePendingOrder(Order order) {
        this.ordersPending.removeIf(o -> o.getNumber() == order.getNumber());
    }

    public void addNumberOfSuccessfulOrders(){ this.numberOfSuccessOrders++; }

    public void decreaseScore() throws Exception {
        int actualScore = this.getScore();
        if (actualScore > 1) // para que no quede un score negativo
            this.setScore(actualScore - 2);
    }


    public void increaseScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }
}
