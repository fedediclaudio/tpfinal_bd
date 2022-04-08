package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="client")
public class Client extends User
{
    //hereda el campo id de User

    @Column(name = "date_of_register_client")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date dateOfRegister;

    //relación uno a muchos con Order
    //Lado Uno
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @OneToMany(mappedBy = "client",  cascade = CascadeType.ALL, orphanRemoval = true)
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
