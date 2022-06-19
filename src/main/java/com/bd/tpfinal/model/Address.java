package com.bd.tpfinal.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Address extends PersistentEntity {

    private String name;

    private String address;

    private String apartment;

    private float[] coords;

    private String description;

    @Transient
    private Client client;

    @DBRef(lazy = true)
    private List<Order> orders = new ArrayList<>();

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

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", apartment='" + apartment + '\'' +
                ", coords=" + Arrays.toString(coords) +
                ", description='" + description + '\'';
    }
}
