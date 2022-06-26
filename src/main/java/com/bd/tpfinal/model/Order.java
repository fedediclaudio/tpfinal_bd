package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import lombok.Data;
import org.springframework.data.annotation.Id;
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

    private OrderStatus status;

    @DBRef
    @CascadePersist
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
}
