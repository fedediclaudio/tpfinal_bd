package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "DeliveryMan")
@AllArgsConstructor
@Data
public class DeliveryMan extends User {

    private int numberOfSuccessOrders = 0;

    private boolean free = true;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfAdmission;

    @DBRef(lazy = true)
    @JsonBackReference(value = "ordersPending")
    private List<Order> ordersPending;


    public DeliveryMan() {
        super();

        this.numberOfSuccessOrders = 0;
        this.free = true;
        this.dateOfAdmission = LocalDate.now();
        this.ordersPending = new ArrayList<Order>();
    }

    public boolean isFree() {
        return free;
    }

    public List<Order> getOrdersPending() {
        return ordersPending;
    }

    public void addPendingOrder(Order order) {
        this.ordersPending.add(order);
    }

    public void removePendingOrder(Order order) {
        this.ordersPending.removeIf(o -> o.getNumber() == order.getNumber());
    }

    public void deductScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore - 2);
    }

    public void addScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (numberOfSuccessOrders < 0) return false;
        return !dateOfAdmission.isAfter(LocalDate.now());
    }
}
