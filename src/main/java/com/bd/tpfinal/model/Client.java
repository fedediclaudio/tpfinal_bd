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

@Document(collection = "Client")
@Data
@AllArgsConstructor
public class Client extends User {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfRegister;

    @DBRef(lazy = true)
    @JsonBackReference
    private List<Order> orders;

    @DBRef(lazy = true)
    private List<Address> addresses;

    public Client() {
        super();
        this.orders = new ArrayList<Order>();
        this.addresses = new ArrayList<Address>();
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
        return addresses.stream().anyMatch(a -> a.getId().equals(address.getId()));
    }

    public void deductScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore - 1);
    }

    public void addScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }

    public boolean isValid() {
        if (!super.isValid()) return false;
        return !dateOfRegister.isAfter(LocalDate.now());
    }

}
