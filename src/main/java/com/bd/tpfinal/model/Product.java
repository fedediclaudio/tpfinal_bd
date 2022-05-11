package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private Long id;

    private String name;

    private float price;

    private float weight;

    private String description;

    //relación muchos a uno con DeliveryMan
    //@JoinColumn: especificar un nombre de columna de clave externa. La clave del otro lado
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_supplier") //nombre del atributo clave del otro lado
    private Supplier supplier;

    //Relacion muchos a uno con ProductType
    //Lado muchos
    //@JoinColumn: especificar un nombre de columna de clave externa. La clave del otro lado
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_productType", nullable = false) //nombre del atributo clave del otro lado
    private ProductType type;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//era LAZY
    @JsonIgnore
    private List<HistoricalProductPrice> prices;

    public Product()
    {
    }

    public Product(String name, float price, float weight, String description, Supplier supplier, ProductType type)
    {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.supplier = supplier;
        this.type = type;
        this.prices = new ArrayList<HistoricalProductPrice>();

        //prices.add(hpp);
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public float getWeight()
    {
        return weight;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Supplier getSupplier()
    {
        return supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    public ProductType getType()
    {
        return type;
    }

    public void setType(ProductType type)
    {
        this.type = type;
    }

    public List<HistoricalProductPrice> getPrices()
    {
        return prices;
    }

    public void setPrices(List<HistoricalProductPrice> prices)
    {
        this.prices = prices;
    }
}
