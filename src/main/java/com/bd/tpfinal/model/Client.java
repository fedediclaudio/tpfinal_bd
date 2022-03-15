package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="clients")
public class Client extends User
{
    //hereda el campo id de User

    @Column(name = "date_of_register", updatable = false, nullable = false)
    //@Temporal(TemporalType.DATE)
    //@JsonProperty("date_of_register")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfRegister;

    //relación uno a muchos con Order
    //Lado Uno
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @OneToMany(mappedBy = "client",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    //relación uno a muchos, unidireccional
    //estamos del lado de uno
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = false)
    private List<Address> addresses;


    public Date getDateOfRegister()
    {
        return dateOfRegister;
    }

    public void setDateOfRegister(Date dateOfRegister)
    {
        this.dateOfRegister = dateOfRegister;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public List<Address> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(List<Address> addresses)
    {
        this.addresses = addresses;
    }
}
