package com.bd.tpfinal.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="addresses")
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@NotNull(message = "address is required")
   	@NotBlank(message ="address can't be blank")
	@Column(length = 100, unique = true)
    private String address;
	
	@Column
    private String apartment;
	
	@Column()
    private float[] coords;
	
	@Column(length = 200)
    private String description;
	
	   
    
    public Address () { /* empty for framework */ }
       
    public Address(String address, String apartment, float[] coords, String description) {
		this.address = address;
		this.apartment = apartment;
		this.coords = coords;
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
        return id;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public float[] getCoords() {
        return coords;
    }

    public void setCoords(float[] coords) {
        this.coords = coords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
