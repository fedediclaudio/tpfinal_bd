package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "productTypes")
public class ProductType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_productType")
    private Long id;

    private String name;

    private String description;

    //relacion uno a muchos con Product
    //lado uno
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @OneToMany(mappedBy = "type",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }
}
