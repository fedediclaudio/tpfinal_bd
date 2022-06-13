package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
public class Item {

    @Id
    private String id;

    private int quantity;

    private String description;

    @DocumentReference
    private Order order;

    @DBRef
    private Product product;
}
