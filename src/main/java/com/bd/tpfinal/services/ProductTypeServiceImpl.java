package com.bd.tpfinal.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	@Autowired ProductTypeRepository productTypeRepository;
	
	@Transactional
	public ProductType saveProductType(ProductType productType) throws Exception {
		return productTypeRepository.save(productType);
	}
	
	public ProductType createNewProductType(ProductType productType) throws Exception {
		// Verifico que tanto el nombre como la descripcion no esten vacíos
		if (productType.getName().isBlank() || 
			productType.getDescription().isBlank()) 
			return null;
		
		// Busco el ProductType en la BD
		ProductType productTypeDB = productTypeRepository.getProductTypeByName(productType.getName());
		
		// Si el ProductoType existe, retorno null, ya que estaría como duplicado
		if (productTypeDB != null) return null;
		
		// Inicializo los atributos
		productType.setId(null);
		productType.setProducts(null);
		
		// Grabo el ProductType 
		return saveProductType(productType);
	}
	
	public List<ProductType> getProductTypeList() throws Exception {
		return productTypeRepository.findAll();
	}
	
}
