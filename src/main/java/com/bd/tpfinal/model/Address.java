package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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

    @DocumentReference
    private Client client;

    private List<Order> orders;
}
