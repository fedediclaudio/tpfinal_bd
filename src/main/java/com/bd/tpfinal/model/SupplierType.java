package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supplierTypes")
public class SupplierType
{
    @Version
    @Column(name = "OBJ_VERSION")
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_supplierType")
    private Long id;

    private String name;

    private String description;

    //relacion uno a muchos con Supplier. Bidireccional (req 6)
    //type es el nombre del atributo que del otro lado
    //referencia a este lado
    @OneToMany(mappedBy = "supplierType",
            cascade = CascadeType.ALL)
    private List<Supplier> suppliers;

    public SupplierType()
    {
    }

    public SupplierType(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.suppliers = new ArrayList<Supplier>();
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Supplier> getSuppliers()
    {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers)
    {
        this.suppliers = suppliers;
    }
}
