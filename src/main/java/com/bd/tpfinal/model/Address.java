package com.bd.tpfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Address")
public class Address {

    @Id
    private ObjectId id;

    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    @DocumentReference
    private Client client;

    private List<Order> orders;

}
