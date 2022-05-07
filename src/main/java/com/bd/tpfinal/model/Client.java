package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "client")
public class Client extends User
{
    //hereda el campo id de User

    @Column(name = "date_of_register_client")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date dateOfRegister;

    //relación uno a muchos con Order
    //Lado Uno
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders;

    //relación uno a muchos, unidireccional
    //estamos del lado de uno
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    //Si le pongo LAZY el postman no me muestra las direcciones cuando pido los Client
    // @OneToMany(mappedBy = "client", fetch = FetchType.LAZY cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<Address> addresses;

    public Client()
    {

    }

    public Client(String name, String username, String password, String email, Date dateOfBirth)
    {
        super(name, username, password, email, dateOfBirth);
        this.orders = new ArrayList<Order>();
        this.addresses = new ArrayList<Address>();
        this.dateOfRegister = new Date();
        System.out.println("date date: "+dateOfRegister);

    }

    public Client(String name, String username, String password, String email, Date dateOfBirth, List<Address> addresses)
    {
        super(name, username, password, email, dateOfBirth);
        this.addresses = addresses;
        this.orders = new ArrayList<Order>();
        this.addresses = new ArrayList<Address>();
        this.dateOfRegister = new Date();
        System.out.println("date date: "+dateOfRegister);
    }

    public Date getDateOfRegister()
    {
        return dateOfRegister;
    }

    public void setDateOfRegister(Date dateOfRegister)
    {
        this.dateOfRegister = dateOfRegister;
    }

    public List<Address> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(List<Address> addresses)
    {
        this.addresses = addresses;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public void addOrder(Order order)
    {
        this.orders.add(order);
    }
}
