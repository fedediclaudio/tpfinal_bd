package com.bd.tpfinal.model;

import java.util.List;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "productType")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_productType", unique = true, updatable = false)
    private int id;

    @Column(nullable = false, length = 50, updatable=true)
    private String name;

    @Column(length = 500, updatable=true)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "productType", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Product> products;

    public ProductType(){}

    public ProductType(String name, String description){
    }
        this.name = name;
        this.description = description;

    /**
     * Getter.
     *
     * @return el nombre del tipo de producto.
     */

    public String getName() {
        return name;
    }
    /**
     * Setter.
     *
     * @param name es el nombre del tipo de producto.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter.
     *
     * @return la descripción del tipo de producto.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Setter.
     *
     * @param description es la descripción del producto.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
