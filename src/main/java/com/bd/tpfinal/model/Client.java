package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    private Date dateOfRegister;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public Date getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(Date dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name="client_orders", joinColumns=@JoinColumn(name="client_id"),
            inverseJoinColumns=@JoinColumn(name="order_id"))
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
