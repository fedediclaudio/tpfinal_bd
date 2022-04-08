package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order", nullable = false, unique = true)
    private Long id;

    private int number;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date dateOfOrder;

    private String comments;

    private float totalPrice;

    @OneToOne(mappedBy = "order", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private OrderStatus status; //acá hay un Patrón State

    //relación muchos a uno con DeliveryMan
    //@JoinColumn: especificar un nombre de columna de clave externa
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "deliveryMan_id")
    //@JsonIgnore //evita bucle infinito al toString.
    private DeliveryMan deliveryMan;

    //relación muchos a uno con Client
    //lado muchos
    //@JoinColumn: especificar un nombre de columna de clave externa. La clave del otro lado
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    //en el UML este campo se llama DeliveryAddress, tal vez se deba cambiar
    //Puede haber muchas Order para un Address
    //lado many
    //contiene la clave externa.
    //tabla secundaria. Lado propietario
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    //@JoinColumn(name = "address_id", nullable = false)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private Address address;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Qualification qualification;

    //relación uno a muchos
    //@OneToMany(cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {}, orphanRemoval = false)

    //@JoinColumn(name = "order_id")
    //"order_id" es el nombre de la columna de tabla items que se agrega para
    //mantener la relación
    private List<Item> items;

    public Long getId()
    {
        return id;
    }
    //solamente puse lo relativo a patrón STATE
    public Order()
    {
        setStatus(new Pending(this));
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Date getDateOfOrder()
    {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder)
    {
        this.dateOfOrder = dateOfOrder;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public float getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public DeliveryMan getDeliveryMan()
    {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan)
    {
        this.deliveryMan = deliveryMan;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Qualification getQualification()
    {
        return qualification;
    }

    public void setQualification(Qualification qualification)
    {
        this.qualification = qualification;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    public OrderStatus getStatus()
    {
        return status;
    }

    public void setStatus(OrderStatus status)
    {
        this.status = status;
    }
}
