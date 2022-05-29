package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    @DocumentReference
    private Client client;

    @DocumentReference
    private List<Order> orders;

}
