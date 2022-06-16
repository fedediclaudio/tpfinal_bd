package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Data
public class DeliveryMan extends User{

    private int numberOfSuccessOrders;

    private boolean free;

    private Date dateOfAdmission;
    @DBRef
    private List<Order> ordersPending;
    /*
    public DeliveryMan(String name, String email, String userName, String password, Date dateOfBirth){
        super (name, email,userName, password, dateOfBirth);
        this.numberOfSuccessOrders = 0;
        this.free = true;
        this.dateOfAdmission = Calendar.getInstance().getTime();
        this.ordersPending = new ArrayList<>();

    }
    */
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
