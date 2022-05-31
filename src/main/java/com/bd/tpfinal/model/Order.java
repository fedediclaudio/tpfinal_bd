package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table (name="Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    private long id;

    private int number;

    private Date dateOfOrder;

    private String comments;

    private float totalPrice;

    @OneToOne( mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  /*  @JoinColumn( name="id_orderStatus" )*/
    private OrderStatus orderStatus;

    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_deliveryMan" )
    private DeliveryMan deliveryMan;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn( name="id_client" )
    private Client client;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn( name="id_address" )
    private Address address;

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_qualification" )
    private Qualification qualification;

    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
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
        return orderStatus;
    }

  public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    /* para cambiar los estados de las ordenes*/
    public void cancelOrder() throws Exception {
        this.orderStatus = new Cancel(this);
    }

    public void refuseOrder() throws Exception {
        this.orderStatus = new Cancel(this);
    }

    public void deliverOrder() throws Exception {
        this.orderStatus = new Sent(this);
    }

    public void finishOrder() throws Exception {
        this.orderStatus = new Delivered(this);
    }

    public boolean addItem(Item item) throws Exception {
        if (this.orderStatus.canAddItem()) {
            this.items.add(item);
            return true;
        }
        return false;
    }

    public void decreaseClientScore() throws Exception {
        this.client.decreaseScore();
    }
    public void increaseClientScore() throws Exception {
        this.client.increaseScore();
    }

}

