package com.bd.tpfinal.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "supplierTypes")
public class SupplierType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_supplierType")
    private Long id;

    private String name;

    private String description;

    //relacion uno a muchos con Supplier. Bidireccional (req 6)
    //type es el nombre del atributo que del otro lado
    //referencia a este lado
    @OneToMany(mappedBy = "type",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Supplier> suppliers;

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
