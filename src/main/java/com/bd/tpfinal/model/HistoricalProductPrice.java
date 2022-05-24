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

     /**
     * CascadeType.MERGE: si no uso esta opción da un error->detached entity passed to persist
     * Es util en los casos en los que sabemos que al modificar la entidad principal sus
     * secundarias también han de ser modificadas porque se han creado en el mismo momento.
     * Por ejemplo, si al modificar un Usuario también hemos modificado su Direccion.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_product", nullable = false) //nombre del atributo clave del otro lado
    private Product product;

    public HistoricalProductPrice()
    {
    }

    public HistoricalProductPrice(float price, Date startDate, Date finishDate, Product product)
    {
        this.price = price;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.product = product;
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

    @Override
    public String toString()
    {
        return "HistoricalProductPrice{" +
                "id=" + id +
                ", price=" + price +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", product=" + product +
                '}';
    }
}
