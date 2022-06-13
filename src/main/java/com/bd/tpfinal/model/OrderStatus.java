package com.bd.tpfinal.model;

import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.Date;

public abstract class OrderStatus {

    private String id;

    private String name;

    private LocalDate startDate;

    @DocumentReference
    private Order order;


    public OrderStatus() {}

    public OrderStatus(String name, LocalDate startDate, Order order) {
        this.name = name;
        this.startDate = startDate;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean canAddItem() {
        return false;
    }

    public boolean canAssign() {
        return false;
    }

    public boolean canRefuse() {
        return false;
    }

    public boolean canDeliver() {
        return false;
    }

    public boolean canFinish() {
        return false;
    }

    public boolean canCancel() {
        return false;
    }

    public boolean canChangeAddress() {
        return false;
    }

    public boolean canRate() {
        return false;
    }

    public boolean assign(DeliveryMan deliveryMan) throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean refuse() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean deliver() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean cancel() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean finish() throws Exception {
        throw new Exception("No se puede realizarse esta accion");
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
