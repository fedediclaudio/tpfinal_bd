package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
@Entity
public class HistoricalProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private float price;

    private Date startDate;

    private Date finishDate;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn( name = "id_product")
    @JsonIgnore
    private Product product;

    public HistoricalProductPrice() {}

    public HistoricalProductPrice(Product product) {
        this.product = product;
        this.price = product.getPrice();
        this.startDate = Calendar.getInstance().getTime();;
        this.finishDate = null;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
