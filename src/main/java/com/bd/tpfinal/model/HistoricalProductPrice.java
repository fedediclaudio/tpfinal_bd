package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="historicalProductPrices")
public class HistoricalProductPrice
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_historicalProductPrice")
    private Long id;

    private float price;

    @Column(name = "startDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date startDate;

    @Column(name = "finishDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date finishDate;

    //Many to One con Product. Bidireccional
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_product", nullable = false) //nombre del atributo clave del otro lado
    private Product product;

    public HistoricalProductPrice()
    {
    }

    public Long getId()
    {
        return id;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getFinishDate()
    {
        return finishDate;
    }

    public void setFinishDate(Date finishDate)
    {
        this.finishDate = finishDate;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }
}
