package com.bd.tpfinal.model;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pruebas")
public class Prueba
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_prueba")
    Long id;

    private String name;
    private int edad;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es_AR")
    private Date cumple;

    public Prueba()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getEdad()
    {
        return edad;
    }

    public void setEdad(int edad)
    {
        this.edad = edad;
    }
}
