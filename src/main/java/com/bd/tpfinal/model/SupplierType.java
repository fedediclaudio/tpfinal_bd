package com.bd.tpfinal.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Document(collection = "SupplierType")
public class SupplierType {

	@Id
    private String id;

	private String name;

	private String description;
	
	@DBRef(lazy = true)
	@JsonBackReference(value = "suppliers")
	private List<Supplier> suppliers;

	
	public SupplierType() {
		this.suppliers = new ArrayList<Supplier>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	
	public void addSupplierToList(Supplier supplier) {
		this.suppliers.add(supplier);
	}

}
