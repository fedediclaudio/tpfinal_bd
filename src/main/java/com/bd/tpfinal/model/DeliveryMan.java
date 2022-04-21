package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "deliverymans")
public class DeliveryMan extends User
{
    //hereda de user el campo id
    /**
     *     @Id
     *     @GeneratedValue(strategy = GenerationType.AUTO)
     *     @Column(name = "id_user")
     *     protected Long id;
     */

    private int numberOfSuccessOrders;

    private boolean free;

    @Column(name = "date_of_Admission_DeliveryMan")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date dateOfAdmission;

    //relación uno a muchos con Order. Lado UNO
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @OneToMany(mappedBy = "deliveryMan",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Order> ordersPending;

    public DeliveryMan()
    {
    }

    public DeliveryMan(String name, String username, String password, String email, Date dateOfBirth, boolean free, Date dateOfAdmission)
    {
        super(name, username, password, email, dateOfBirth);
        this.numberOfSuccessOrders = 0;
        this.free = free;
        this.dateOfAdmission = dateOfAdmission;
    }

    public int getNumberOfSuccessOrders()
    {
        return numberOfSuccessOrders;
    }

    public void setNumberOfSuccessOrders(int numberOfSuccessOrders)
    {
        this.numberOfSuccessOrders = numberOfSuccessOrders;
    }

    public boolean isFree()
    {
        return free;
    }

    public void setFree(boolean free)
    {
        this.free = free;
    }

    public Date getDateOfAdmission()
    {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission)
    {
        this.dateOfAdmission = dateOfAdmission;
    }

    public List<Order> getOrdersPending()
    {
        return ordersPending;
    }

    public void setOrdersPending(List<Order> ordersPending)
    {
        this.ordersPending = ordersPending;
    }
}
