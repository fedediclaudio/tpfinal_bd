package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product", unique = true, updatable = false)
    private Long id;

    @Column(nullable = false, length = 50, updatable=true)
    private String name;

    @Column(nullable = false, updatable=true)
    private float price;

    @Column(updatable=true)
    private float weight;

    @Column(length = 500, updatable=true)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "id_supplier", nullable = false)
    private Supplier supplier;

    @Version
    @Column(name = "version")
    private int version;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "product_product_type",
            joinColumns = { @JoinColumn(name = "id_product") },
            inverseJoinColumns = { @JoinColumn(name = "id_product_type") }
    )
    private List<ProductType> productType;
    
    public Product(){}

    public Product(String name, float price, float weight, String description, Supplier supplier){
        this.name = name;
        this.price=price;
        this.weight=weight;
        this.description = description;
        this.productType=new ArrayList<>();
        this.supplier = supplier;
    }
    /**
     * Getter.
     *
     * @return el id del producto.
     */
    public Long getId() {
        return this.id;
    }
    /**
     * Getter.
     *
     * @return el nombre del producto.
     */
    public String getName() {
        return name;
    }
    /**
     * Setter.
     *
     * @param name es el nombre del producto.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter.
     *
     * @return el precio del producto.
     */
    public float getPrice() {
        return price;
    }
    /**
     * Setter.
     *
     * @param price es el precio del producto.
     */
    public void setPrice(float price) {
        this.price = price;
    }
    /**
     * Getter.
     *
     * @return el peso del producto.
     */
    public float getWeight() {
        return weight;
    }
    /**
     * Setter.
     *
     * @param weight es el peso del producto.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }
    /**
     * Getter.
     *
     * @return la descripción del producto.
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<ProductType> getType() {
        return productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public void addProductType(ProductType productType) {this.productType.add(productType);}

    public void removeProductType(ProductType productType) {this.productType.remove(productType);}


    public List<ProductType> getProductType() {
        return this.productType;
    }

    public void setPrices(List<ProductType> productType) {
        this.productType = productType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void update(Product product) {
        this.setName(product.getName());
        this.setWeight(product.getWeight());
        this.setName(product.getName());
        this.setDescription(product.getDescription());
        this.setSupplier(product.getSupplier());
        this.setPrice(product.getPrice());
    }
}
