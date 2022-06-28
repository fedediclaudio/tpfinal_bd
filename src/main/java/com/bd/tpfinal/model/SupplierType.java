package com.bd.tpfinal.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="suppliers_type")
public class SupplierType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "name is required")
   	@NotBlank(message ="name can't be blank")
	@Column(unique = true) 
    private String name;
	
	@Column()
    private String description;
       
	@Version
	private int version;
	
    public SupplierType(){ /* empty for framework */ }
    
    public SupplierType(String name, String description) {
		
    	this.name = name;
		this.description = description;
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
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
