package com.bd.tpfinal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="products")

public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId 
	private ProductPK productPK;
		
	@NotNull(message ="price is required")
	@Column()
	private float price;

    private float weight;

    private String description;
    
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "products_types_mapped",
	    joinColumns = {@JoinColumn(name ="name_product", referencedColumnName = "name"), @JoinColumn(name ="idsupplier_product", referencedColumnName = "id_supplier")},
	    inverseJoinColumns = @JoinColumn (name = "id_product_type", referencedColumnName = "id"))
    private Set<ProductType> types = new HashSet<>();
    
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({@JoinColumn(name ="name_product", referencedColumnName = "name"),
				  @JoinColumn(name ="idsupplier_product", referencedColumnName ="id_supplier")})
    private List<HistoricalProductPrice> prices = new ArrayList<HistoricalProductPrice>();

	@Column()
    private boolean active;
    
    @Version
	private int version; 
    
    public Product() { /* empty for framework */ }   	
    	
    public Product(String name, float price, float weight, String description, Supplier supplier) {
    	this.productPK = new ProductPK(name, supplier.getId());
		this.price = price;
		this.weight = weight;
		this.description = description;
		this.prices = new ArrayList<HistoricalProductPrice>();
	}   
    
    public ProductPK getProductPK() {
    	return productPK;
	}

	public void setProductPK(ProductPK productPK) {
		this.productPK = productPK;
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
