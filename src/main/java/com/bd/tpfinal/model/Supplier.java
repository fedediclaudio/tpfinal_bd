package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;
@Document(collection = "suppliers")
public class Supplier extends PersistentEntity{

    private String name;

    private String cuil;

    private String address;

    private float[] coords;
    @JsonProperty("qualification_of_users")
    private float qualificationOfUsers;

    @DBRef(lazy = true)
    private List<Product> products = new ArrayList<>();

    private SupplierType type;
    @Version
    private Long version;

    public Supplier(){

    }

    public Supplier(String id, String name, String cuil, String address, float[] coords, float qualificationOfUsers, SupplierType type) {
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
        products.size();
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

    public void remove(Product product){
        products.size();
        products.remove(product);
    }
}
