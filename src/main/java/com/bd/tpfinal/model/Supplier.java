package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.List;
@Entity
public class Supplier {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;
    @ManyToMany(mappedBy = "supplier" )
    private List<Product> products;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "supplier_supplierType",  joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "supplierType_id"))
    private List<SupplierType> type;

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

    public List<SupplierType> getType() {
        return type;
    }

    public void setType(List<SupplierType> type) {
        this.type = type;
    }
}
