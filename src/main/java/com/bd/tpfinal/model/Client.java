package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Client extends User{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Date dateOfRegister;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany
    private List<Address> addresses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client(){}

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
}
