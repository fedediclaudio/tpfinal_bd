package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public abstract class OrderStatus {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private Date startDate;
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name="id_order" )
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean canAddItem() { return false; }

    public boolean canAssign() { return false; }

    public boolean canRefuse() { return false; }

    public boolean canDeliver() { return false; }

    public boolean canFinish() { return false; }

    public boolean canCancel() { return false; }

    public boolean addItem() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean assign(DeliveryMan deliveryMan) throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean refuse() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean deliver() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean cancel() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public boolean finish() throws Exception{
        throw new Exception("No se puede realizarse esta accion");
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
