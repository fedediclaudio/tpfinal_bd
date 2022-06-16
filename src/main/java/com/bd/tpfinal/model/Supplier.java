package com.bd.tpfinal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Document(collection = "Supplier")
public class Supplier {

	@Id
    private String id;

	private String name;

	private String cuil;

	private String address;

	private float[] coords;

	private float qualificationOfUsers;

	@DBRef(lazy = true)
	@JsonBackReference(value = "products")
	private List<Product> products;
	
	private SupplierType type;

	
	public Supplier() {
		this.products = new ArrayList<Product>();
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

	public float[] getCoords() {
		return coords;
	}

	public void setCoords(float[] coords) {
		this.coords = coords;
	}

	public float getQualificationOfUsers() {
		return qualificationOfUsers;
	}
	
	public void setQualificationOfUsers(float qualificationOfUsers) {
		this.qualificationOfUsers = qualificationOfUsers;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	public SupplierType getType() {
		return type;
	}

	public void setType(SupplierType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", cuil=" + cuil + ", address=" + address + ", coords="
				+ Arrays.toString(coords) + ", qualificationOfUsers=" + qualificationOfUsers + ", products=" + products
				+ ", type=" + type + "]";
	}
	
}
