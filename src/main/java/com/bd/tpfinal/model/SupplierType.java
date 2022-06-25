package com.bd.tpfinal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SupplierType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_suplier_type")
	private Long id;

	private String name;

	private String description;

	@JsonIgnore
	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Supplier> suppliers;

	
	public SupplierType() {
		this.suppliers = new ArrayList<>();
	}

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

	@Override
	public String toString() {
		return "SupplierType [id=" + id + ", name=" + name + ", description=" + description + ", suppliers=" + suppliers
				+ "]";
	}

}
