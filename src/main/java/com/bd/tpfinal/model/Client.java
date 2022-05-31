package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
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

    public Client (){}
    public Client (String name, String email, String userName, String password, Date dateOfBirth){
        super(name, email, userName, password, dateOfBirth);
        this.dateOfRegister = Calendar.getInstance().getTime();
        this.orders = new ArrayList<>();
    }
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

    public void addOrder(Order order){this.orders.add(order);}


    public void decreaseScore() throws Exception {
        int actualScore = this.getScore();
        if (actualScore > 0) // para que no quede un score negativo
            this.setScore(actualScore - 1);
    }


    public void increaseScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }

}
