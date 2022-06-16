package com.bd.tpfinal.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
@Data
@Document
public class Address {
    @Id
    private String id;
    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    @DBRef
    private Client client;
    @DBRef
    private List<Order> orders;
}
