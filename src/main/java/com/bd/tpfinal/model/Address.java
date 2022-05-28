package com.bd.tpfinal.model;

import lombok.Data;

import java.util.List;

@Data
public class Address {

    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    private Client client;

    private List<Order> orders;

}
