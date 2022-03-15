package com.bd.tpfinal.model;

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

    private Date startDate;

    private Date finishDate;

    //Many to One con Product. Bidireccional
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_product", nullable = false) //nombre del atributo clave del otro lado
    private Product product;

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
