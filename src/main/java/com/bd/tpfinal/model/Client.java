package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Client")
public class Client extends User {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfRegister;

    @DocumentReference
    private List<Order> orders;

    @DBRef
    private List<Address> addresses;

    public LocalDate getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(LocalDate dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public Order getOrder(Order order) {
        int idx = this.orders.indexOf(order);
        if (idx == -1) return null;
        return this.orders.get(idx);
    }


    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        this.orders.removeIf(o -> o.getNumber() == order.getNumber());
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public boolean hasAddress(Address address) {
        return addresses.contains(address);
    }

    public void deductScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore - 1);
    }

    public void addScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }

}
