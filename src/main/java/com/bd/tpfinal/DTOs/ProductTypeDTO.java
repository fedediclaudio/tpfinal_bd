package com.bd.tpfinal.DTOs;

public class ProductTypeDTO {

    public ProductTypeDTO() {}

    public ProductTypeDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
