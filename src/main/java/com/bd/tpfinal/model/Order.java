package com.bd.tpfinal.model;

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
    @Column(name = "number_id_order", unique = true, updatable = false)
    private Long number;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    @Column(nullable = false, updatable = false)
    private Date dateOfOrder;

    @Column(length = 500)
    private String comments;

    @Column(nullable = false)
    private float totalPrice;

    //https://www.baeldung.com/jpa-embedded-embeddable
    // @Embedded se usa para incrustar un tipo en otra entidad.
    @Embedded
    private OrderStatus orderStatus; //acá hay un Patrón State

    //relación muchos a uno con DeliveryMan
    //@JoinColumn: especificar un nombre de columna de clave externa
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "deliveryMan_id")
    private DeliveryMan deliveryMan;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_user", nullable = false)
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
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {}, orphanRemoval = false)
    //@JoinColumn(name = "order_id")
    //"order_id" es el nombre de la columna de tabla items que se agrega para
    //mantener la relación
    //@JsonIgnore
    private List<Item> items;

    //solamente puse lo relativo a patrón STATE
    public Order()
    {
        this.orderStatus = new Pending(this,this.getDateOfOrder());
    }

    public Order(Date dateOfOrder, String comments, float totalPrice, Client client, Address address)
    {
        this.dateOfOrder = dateOfOrder;
        this.comments = comments;
        this.totalPrice = totalPrice;
        this.deliveryMan = null;
        this.client = client;
        this.address = address;
        this.qualification = null;
        this.items = null;
        this.orderStatus = new Pending(this,dateOfOrder);
    }

    public Long getNumber()
    {
        return number;
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

    /**
     * Solamente puede asignar un repartidor si el estado es pending.
     * @param deliveryMan
     */
    public void setDeliveryMan(DeliveryMan deliveryMan)
    {
        //this.deliveryMan = deliveryMan;
        try
        {
            this.orderStatus.assign(deliveryMan);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

    public OrderStatus getOrderStatus()
    {
        //return Status_Factory.getInstanceByEnum(status);
        return orderStatus;
        //return Status_Factory.getInstanceByOrderStatus(status);
    }

    public void setOrderStatus(OrderStatus orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    /**
     * establece el objeto hijo de OrderStatus
     */
    public void setStatusByName()
    {
        switch (orderStatus.getName())
        {
            case "Pending":
                this.setOrderStatus(new Pending(this, this.orderStatus.getStartDate()));
                break;
            case "Assigned":
                this.setOrderStatus(new Assigned(this, this.orderStatus.getStartDate()));
                break;
            case "Sent":
                this.setOrderStatus(new Sent(this, this.orderStatus.getStartDate()));
                break;
            case "Delivered":
                this.setOrderStatus(new Delivered(this, this.orderStatus.getStartDate()));
                break;
            case "Cancelled":
                this.setOrderStatus(new Cancelled(this, this.orderStatus.getStartDate()));
                break;
        }
    }

}
