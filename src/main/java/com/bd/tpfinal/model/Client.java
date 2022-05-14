package com.bd.tpfinal.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "clients")
public class Client extends User {

    private Date dateOfRegister;
    @DBRef(lazy = true)
    private List<Order> orders = new ArrayList<>();

    private List<Address> addresses = new ArrayList<>();

    public Date getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(Date dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addOrder(Order order) {
        this.getOrders().size();
        this.orders.add(order);
    }

    public void add(Address address) {
        this.addresses.size();
        this.addresses.add(address);
    }

    public Address getAddress(int i) {
        addresses.size();
        return addresses.get(i);
    }
}
