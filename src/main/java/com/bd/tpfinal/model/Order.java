package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;

@Data
public class Order {

    @Id
    private String id;

    private int number;

    private Date dateOfOrder;

    private String comments;

    private float totalPrice;

    @DBRef
    @CascadePersist
    private OrderStatus orderStatus;

    @DBRef
    @CascadePersist
    @ToString.Exclude
    private DeliveryMan deliveryMan;
    @DBRef
    @CascadePersist
    private Client client;
    @DBRef
    @CascadePersist
    private Address address;
    @DBRef
    @CascadePersist
    private Qualification qualification;
    @DBRef
    private List<Item> items;
    @Version
    private int version;

    public OrderStatus getStatus() {
        return orderStatus;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public Supplier getSupplier() {

        return this.items.get(1).getProduct().getSupplier();
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



    public void updateQualification(Qualification qualification) throws Exception {
        this.qualification =  qualification; // la orden

    }

}
