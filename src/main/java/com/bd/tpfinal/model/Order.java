package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table (name="Orders")
public class Order {
    @Id
    @Column (name = "id", nullable = false)
    private long id;

    private int number;

    private Date dateOfOrder;

    private String comments;

    private float totalPrice;
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_orderStatus" )
    private OrderStatus status;

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_deliveryMan" )
    private DeliveryMan deliveryMan;

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_client" )
    private Client client;


    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_address" )
    private Address address;

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_qualification" )
    private Qualification qualification;

    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Item> items;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

 public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}

