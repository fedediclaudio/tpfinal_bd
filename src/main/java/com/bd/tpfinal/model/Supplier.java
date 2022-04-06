package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class Supplier
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_supplier")
    private Long id;

    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;

    //one to many con Product. Un proveedor con muchos productos
    //relación uno a muchos con Order. Lado UNO
    // mappedBy: nombre del atributo del otro (muchos) lado que referencia a este lado (uno)
    @OneToMany(mappedBy = "supplier",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    //relacion bidireccional muchos a uno.
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "supplierType_column")
    @JsonIgnore //evita bucle infinito al toString
    private SupplierType supplierType;

    public Supplier()
    {
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

    public String getCuil()
    {
        return cuil;
    }

    public void setCuil(String cuil)
    {
        this.cuil = cuil;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public float[] getCoords()
    {
        return coords;
    }

    public void setCoords(float[] coords)
    {
        this.coords = coords;
    }

    public float getQualificationOfUsers()
    {
        return qualificationOfUsers;
    }

    public void setQualificationOfUsers(float qualificationOfUsers)
    {
        this.qualificationOfUsers = qualificationOfUsers;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public SupplierType getSupplierType()
    {
        return supplierType;
    }

    public void setSupplierType(SupplierType supplierType)
    {
        this.supplierType = supplierType;
    }
}
