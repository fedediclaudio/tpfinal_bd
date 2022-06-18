package com.bd.tpfinal.model;

import com.bd.tpfinal.annotation.CascadePersist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @CascadePersist
    private Client client;
    @DBRef
    @JsonBackReference
    private List<Order> orders;
}
