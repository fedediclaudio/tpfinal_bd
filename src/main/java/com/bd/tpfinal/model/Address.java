package com.bd.tpfinal.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="address")
/**
 * Dirección/es del Client.
 * Se elige una para vincular con el pedido (Order)
 * Algunos campos se completan al crear la dirección y otros luego.
 */
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_address")
    private Long id;

    //como no se para que se usa este parámetro
    //lo interpreto como un identificador de dirección
    //por ejemplo: "casa", "trabajo", "oficina", etc.
    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    // un Client puede tener muchas direcciones
    // estamos del lado de muchos.
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "user_id")
   // @JsonBackReference //evita bucle infinito al toString
    //como es unidireccional de este lado no se pone nada.
    //@Column(name = "client")
    @JsonIgnore
    private Client client;

    //ordenes recien creadas, estado inicial pending.
    //al entregarse la orden o al cancelarse
    //se saca de esta lista.
    //Se carga al elegir la dirección del Client.
    @OneToMany(mappedBy = "address")
    private List<Order> ordersPending;

    //relación uno a muchos con Order. Lado UNO
    //tabla principal de la relación.
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @OneToMany(mappedBy = "address")
    private List<Order> orders;

    public Address()
    {
    }

    public Long getId()
    {
        return id;
    }
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
