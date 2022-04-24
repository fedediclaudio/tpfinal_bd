package com.bd.tpfinal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Entity
@DiscriminatorValue("SUPPLIER")
@Table(name = "suppliers")
public class Supplier extends PersistentEntity{

    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name="supplier_products",
            joinColumns=@JoinColumn(name="supplier_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    private List<Product> products = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    private SupplierType type;

    @Version
    private Long version;

    public Supplier(){

    }

    public Supplier(Long id, String name, String cuil, String address, float[] coords, float qualificationOfUsers, SupplierType type) {
        setId(id);
        this.name = name;
        this.cuil = cuil;
        this.address = address;
        this.coords = coords;
        this.qualificationOfUsers = qualificationOfUsers;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float[] getCoords() {
        return coords;
    }

    public void setCoords(float[] coords) {
        this.coords = coords;
    }

    public float getQualificationOfUsers() {
        return qualificationOfUsers;
    }

    public void setQualificationOfUsers(float qualificationOfUsers) {
        this.qualificationOfUsers = qualificationOfUsers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public SupplierType getType() {
        return type;
    }

    public void setType(SupplierType type) {
        this.type = type;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    public void addProduct(Product product){
        products.size();
        products.add(product);
    }
}
