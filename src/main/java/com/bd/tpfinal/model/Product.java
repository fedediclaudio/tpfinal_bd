package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private float price;

    private float weight;

    private String description;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType type;

    @OneToMany
    private List<HistoricalProductPrice> prices;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public List<HistoricalProductPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<HistoricalProductPrice> prices) {
        this.prices = prices;
    }
}
