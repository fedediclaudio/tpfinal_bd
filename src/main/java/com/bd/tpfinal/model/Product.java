package com.bd.tpfinal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@MongoId 
	@JsonSerialize(using= ToStringSerializer.class)
	private ObjectId id;
	
	@Field
	private String name;
		
	@NotNull(message ="price is required")
	@Field
	private float price;
	
	@Field
    private float weight;
	
	@Field
    private String description;
    
    @DBRef
    private Set<ProductType> types = new HashSet<>();
    
    @DBRef
    @JsonIgnore
    private Supplier supplier;
    
    @Field
    private List<HistoricalProductPrice> prices = new ArrayList<HistoricalProductPrice>();

	@Field
    private boolean active;
    
    @Version
	private int version; 
    
    public Product() { /* empty for framework */ }   	
    	
    public Product(String name, float price, float weight, String description, Supplier supplier) {
    	this.name = name;
    	this.supplier = supplier;
		this.price = price;
		this.weight = weight;
		this.description = description;
		this.prices = new ArrayList<HistoricalProductPrice>();
	}   
    
	
    public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
    
    public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductType> getTypes() {
		return types;
	}

	public void setTypes(Set<ProductType> types) {
		this.types = types;
	}

	public List<HistoricalProductPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<HistoricalProductPrice> prices) {
        this.prices = prices;
    }    
   
    public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void addProductType(ProductType productType) { this.types.add(productType); }		
	
	public void deleteProductType(ProductType productType) { this.types.remove(productType); }

	public void addHistoricalProductPrice(HistoricalProductPrice historicalProductPrice) { this.prices.add(historicalProductPrice); }

}
