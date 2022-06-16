package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


// https://spring.io/blog/2021/11/29/spring-data-mongodb-relation-modelling

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Address")
public class Address {

    @Id
    private String id;

    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    @DBRef(lazy = true)
    @JsonBackReference
    private Client client;

    @DBRef(lazy = true)
    @JsonBackReference(value = "orders")
    private List<Order> orders;

    public boolean isValid() {
        if (name.isBlank()) return false;
        if (address.isBlank()) return false;
        if ((coords == null) || (coords.length != 2)) return false;
        if (description.isBlank()) return false;

        return true;
    }
}
