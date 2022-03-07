package com.bd.tpfinal.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_supplier")
	private Long id;

	private String name;

	private String cuil;

	private String address;

	private float[] coords;

	private float qualificationOfUsers;

	@JsonIgnore
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_supplier_type")
	private SupplierType type;

	
	public Supplier() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
