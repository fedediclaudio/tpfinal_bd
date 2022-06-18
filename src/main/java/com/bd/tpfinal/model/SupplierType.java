package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
public class SupplierType {
    @Id
    private String id;

    private String name;

    private String description;
    @DocumentReference
    private List<Supplier> suppliers;
}
