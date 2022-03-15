package com.bd.tpfinal.model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="address")
//todo atributo es persistente a menos que se lo declare transient

public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_address")
    private Long id;

    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    // un Client puede tener muchas direcciones
    // estamos del lado de muchos (Client)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_user", nullable = false)
    @JsonBackReference //evita bucle infinito al toString
    //como es unidireccional de este lado no se pone nada.
    //al crearse la tabla se agrega automaticamente la columna "id_client"
    //que esta especificada del lado del Client en la @OneToMany
    //@Column(name = "client")
    private Client client;

    //private List<Order> ordersPending;

    //relación uno a muchos con Order. Lado UNO
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getApartment()
    {
        return apartment;
    }

    public void setApartment(String apartment)
    {
        this.apartment = apartment;
    }

    public float[] getCoords()
    {
        return coords;
    }

    public void setCoords(float[] coords)
    {
        this.coords = coords;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }
}
