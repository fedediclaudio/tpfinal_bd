package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DeliveryMan extends User {

    public DeliveryMan()  {
        this.ordersPending = new ArrayList<>();
    }

    private int numberOfSuccessOrders;

    private boolean free;

    private boolean active;

    private Date dateOfAdmission;
    @DBRef
    @JsonBackReference("ordersPending")
    private List<Order> ordersPending;

    public void addOrder(Order order) {
        this.ordersPending.add(order);
    }

    public void deleteOrder(Order order) {
        this.ordersPending.remove(order);
    }

    public void removePendingOrder(Order order) {
        this.ordersPending.removeIf(o -> o.getNumber() == order.getNumber());
    }

    public void addNumberOfSuccessfulOrders() {
        this.numberOfSuccessOrders++;
    }

    public void decreaseScore() throws Exception {
        int actualScore = this.getScore();
        if (actualScore > 1)
            this.setScore(actualScore - 2);
    }

    public void increaseScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }
}
