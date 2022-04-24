package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "product_types")
public class ProductType extends PersistentEntity{

    private String name;

    private String description;
    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(name="products_products_types",
            joinColumns=@JoinColumn(name="product_id"),
            inverseJoinColumns=@JoinColumn(name="product_type_id"))
    private List<Product> products = new ArrayList<>();

    @Version
    private Long version;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void addProduct(Product product){
        if (products == null)
            products = new ArrayList<>();
        products.add(product);
        product.setType(this);
    }
}
