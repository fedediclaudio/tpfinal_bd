package com.bd.tpfinal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPK implements Serializable{
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name", updatable = false, nullable = false)
	private String name;
	@Column(name = "id_supplier")
	private Long idSupplier;

    
    public ProductPK() { /* empty for framework */ }


	public ProductPK(String name, Long idSupplier) {
		this.name = name;
		this.idSupplier = idSupplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdsupplier() {
		return idSupplier;
	}

	public void setIdsupplier(Long idSupplier) {
		this.idSupplier = idSupplier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSupplier == null) ? 0 : idSupplier.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPK other = (ProductPK) obj;
		if (idSupplier == null) {
			if (other.idSupplier != null)
				return false;
		} else if (!idSupplier.equals(other.idSupplier))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    
}
