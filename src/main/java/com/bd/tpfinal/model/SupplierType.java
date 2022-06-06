package com.bd.tpfinal.model;
import lombok.Data;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
public class SupplierType {
    @Id
    private String id;

    private String name;

    private String description;
    @DocumentReference
    private List<Supplier> suppliers;
}
