package com.bd.tpfinal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="suppliers")
public class Supplier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = " id_supplier")
	private Long id;
	
	@NotNull(message ="name is required")
	@NotBlank(message ="name can't be blank")	
	@Column()
    private String name;

    private String cuil;
    
    @NotNull(message ="address is required")
	@NotBlank(message ="address can't be blank")
    @Column(unique = true)
    private String address;

    private float[] coords;

    private float qualification; // private float qualificationOfUsers;
   
    private int numberOfQualif;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL) 
    @MapsId("idSupplier")
    @JoinColumn(name = "id_supplier", referencedColumnName = "id_supplier",  insertable = false, updatable = false)
    private Set<Product> products;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "suppliers_types_mapped",
    		   joinColumns = @JoinColumn (name = "id_supplier"), inverseJoinColumns = @JoinColumn (name = "id_supplier_type")) 	
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
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
