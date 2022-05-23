package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@DiscriminatorValue("Cliente")
public class Client extends User{

    private Date dateOfRegister;
    @OneToMany( mappedBy = "client", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY )
    private List<Order> orders;
    @OneToMany( mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

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
