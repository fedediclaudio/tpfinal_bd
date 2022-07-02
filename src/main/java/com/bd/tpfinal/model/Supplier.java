package com.bd.tpfinal.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


@Document
public class Supplier {
	
	@MongoId
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	
	@NotNull(message ="name is required")
	@NotBlank(message ="name can't be blank")	
	@Field
    private String name;
	
	@Field
    private String cuil;
    
    @NotNull(message ="address is required")
	@NotBlank(message ="address can't be blank")
    @Field
    private String address;
    
    @Field
    private float[] coords;
    
    @Field
    private float qualification; // private float qualificationOfUsers;
    
    @Field
    private int numberOfQualif;
    
    @DBRef
    private Set<Product> products = new HashSet<>();
    
    @DBRef
    private Set<SupplierType> types = new HashSet<>();
   
    @Version
	private int version;
    

	public Supplier(){ /* empty for framework */ }
    
       
    public Supplier(String name, String cuil, String address, float[] coords) {
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.coords = coords;
		this.qualification = 0;
		this.numberOfQualif = 0;
		this.products = new HashSet<>();		
	}
    
    public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
    public String getName() {
        return this.name;
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
        return this.address; 
    }

    public void setAddress(String address) {
    	this.address= address; 
    }

    public float[] getCoords() {
        return coords;
    }

    public void setCoords(float[] coords) {
        this.coords = coords;
    }

	public float getQualification() {
		return qualification;
	}

	public void setQualification(float qualification) {
		this.qualification = qualification;
	}

	public int getNumberOfQualif() {
		return numberOfQualif;
	}

	public void setNumberOfQualif(int numberOfQualif) {
		this.numberOfQualif = numberOfQualif;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<SupplierType> getTypes() {
		return types;
	}

	public void setTypes(Set<SupplierType> types) {
		this.types = types;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void addSupplierType(SupplierType supplierType) { this.types.add(supplierType); }	
	
	public void deleteSupplierType(SupplierType supplierType) { this.types.remove(supplierType); }			
    
	public void addProduct(Product prod) { this.products.add(prod); }		
		
	public void deleteProduct(Product prod) { this.products.remove(prod); }

		
}
