package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;
@Data
public class Supplier {
    //@Id
    //private String id;

    private String name;

    private String cuil;

    private String address;

    private float[] coords;

    private float qualificationOfUsers;

    private List<Product> products;

    private SupplierType type;
}
