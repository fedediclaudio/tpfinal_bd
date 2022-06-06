package com.bd.tpfinal.model;

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

    private DeliveryMan deliveryMan;

    private Client client;

    private Address address;

    private Qualification qualification;

    private List<Item> items;

}
