package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Orders")
public class Order {

    private ObjectId id;

    private int number;

    private Date dateOfOrder;

    private String comments;

    private float totalPrice;

    private OrderStatus status;

    @DocumentReference
    private DeliveryMan deliveryMan;

    @DocumentReference
    private Client client;

    @DocumentReference
    private Address address;

    private Qualification qualification;

    @DBRef
    private List<Item> items;

}
