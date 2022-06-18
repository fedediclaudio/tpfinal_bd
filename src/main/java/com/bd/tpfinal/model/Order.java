package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import lombok.Data;
import org.springframework.data.annotation.Id;

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

    @CascadePersist
    private DeliveryMan deliveryMan;
    @CascadePersist
    private Client client;
    @CascadePersist
    private Address address;
    @CascadePersist
    private Qualification qualification;

    private List<Item> items;

}
