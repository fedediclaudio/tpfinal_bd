package com.bd.tpfinal.model;
import lombok.Data;
import java.util.List;
import org.springframework.data.annotation.Id;
@Data
public class SupplierType {
    @Id
    private String id;

    private String name;

    private String description;

    private List<Supplier> suppliers;
}
