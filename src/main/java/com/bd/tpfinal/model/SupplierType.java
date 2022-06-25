package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference("suppliers")
    private List<Supplier> suppliers;
}
