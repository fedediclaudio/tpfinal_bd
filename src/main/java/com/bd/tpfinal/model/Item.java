package com.bd.tpfinal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="items")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(length = 600)
    private String description;

	@NotNull(message ="product is required")
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumns({
    	@JoinColumn(name ="name_product", referencedColumnName = "name"),
    	@JoinColumn(name ="idsupplier_product", referencedColumnName ="id_supplier")
    })
    private Product product;
    
	@Version
	private int version;
    
    public Item() {	/* empty for framework */ }    
    
    public Item(Product product, int quantity, String description) {
		this.quantity = quantity;
		this.description = description;
		this.product = product;
	}    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity; 
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
   
}
