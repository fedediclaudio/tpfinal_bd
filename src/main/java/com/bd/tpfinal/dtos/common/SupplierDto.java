package com.bd.tpfinal.dtos.common;

import com.bd.tpfinal.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SupplierDto {

    @JsonProperty("supplier_id")
    private String supplierId;
    private String name;
    private String cuil;
    private String address;
    @JsonProperty("qualification_of_users")
    private float qualificationOfUsers;
    List<ProductDto> products;
    @JsonProperty("supplier_type")
    String supplierType;

    public SupplierDto() {
    }

    public SupplierDto(String supplierId, String name, String cuil, String supplierType) {
        this.supplierId = supplierId;
        this.name = name;
        this.cuil = cuil;
        this.supplierType = supplierType;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getQualificationOfUsers() {
        return qualificationOfUsers;
    }

    public void setQualificationOfUsers(float qualificationOfUsers) {
        this.qualificationOfUsers = qualificationOfUsers;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }
}
