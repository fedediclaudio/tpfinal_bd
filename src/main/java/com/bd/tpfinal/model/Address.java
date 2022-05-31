package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
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

    private Client client;

    private List<Order> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public float[] getCoords() {
        return coords;
    }

    public void setCoords(float[] coords) {
        this.coords = coords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Address(String name, String address, String apartment, float[] coords, String description, Client client, List<Order> orders) {
        this.name = name;
        this.address = address;
        this.apartment = apartment;
        this.coords = coords;
        this.description = description;
        this.client = client;
        this.orders = orders;
    }
}
