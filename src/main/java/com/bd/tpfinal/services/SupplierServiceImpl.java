package com.bd.tpfinal.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;
import com.bd.tpfinal.repositories.implementations.SupplierTypeRepository;

@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired SupplierTypeRepository supplierTypeRepository;
	@Autowired ProductTypeRepository productTypeRepository;
	@Autowired SupplierRepository supplierRepository;
	
	@Transactional
	public Supplier saveSupplier(Supplier supplier) throws Exception {
		return supplierRepository.save(supplier);
	}
	
	public Supplier createNewSupplier(Supplier supplier) throws Exception {
		// Verifico todos los campos
		if (supplier.getName().isBlank()) return null;
		if (supplier.getCuil().isBlank()) return null;
		if (supplier.getAddress().isBlank()) return null;
		if (supplier.getCoords().length != 2) return null;
		if (supplier.getType() == null) return null;
		
		// Busco el SupplierType en la BD
		SupplierType supplierType = supplierTypeRepository.getSupplierTypeById(supplier.getType().getId());
		
		// Si el SupplierType no existe, retorno null
		if (supplierType == null) {
			System.out.println("El SupplierType no existe");
			return null;
		}
		
		// Lo sobreescribo para que el supplier tenga la referencia de la BD
		supplier.setType(supplierType);
		
		// Inicializo los otros atributos
		supplier.setId(null);
		supplier.setProducts(null);
		supplier.setQualificationOfUsers(0);
		
		// Grabo el Supplier
		return saveSupplier(supplier);
	}
	
	public List<Supplier> getSupplierList() throws Exception {
		return supplierRepository.findAll();
	}

	public List<Supplier> getSupplierListFromType(long idSupplierType) throws Exception {
		return supplierRepository.getSupplierListFromType(idSupplierType);
	}
	
	public List<Supplier> getTopTen() throws Exception {
		return supplierRepository.getTopTen();
	}
	
	public List<Supplier> getSupplierWithAtLeastOneStar() throws Exception {
		return supplierRepository.getSupplierWithAtLeastOneStar();
	}
	
	public List<Supplier> getSupplierWhoOfferAllProducts() throws Exception {
		// Lista que va a contener todos los Suppliers que ofrecen productos de todos los tipos
		List<Supplier> offer = new ArrayList<Supplier>();
		
		// Obtengo todos los tipos de producto y todos los suppliers
		List<ProductType> allTypes = productTypeRepository.findAll();
		List<Supplier> allSuppliers = supplierRepository.findAll();
		
		// Recorro todos los Suppliers 
		for (Supplier supplier : allSuppliers) {
			// Si el Supplier tiene todos los tipos, lo agrego a la lista
			if (hasAllSupplierTypes(supplier.getProducts(), allTypes))
				offer.add(supplier);
		}
			
		return offer;
	}
	
	private boolean hasAllSupplierTypes(List<Product> products, List<ProductType> types) {
		// Creo una copia de la los tipos de productos
		List<ProductType> actualTypes = new ArrayList<ProductType>();		
		actualTypes.addAll(types);
		
		int idx = 0;
		while ((idx < products.size())) {
			Product actual = products.get(idx);
			// Elimino el tipo de producto de la lista de tipos
			actualTypes.removeIf(t -> t.getId() == actual.getType().getId());
			idx ++;
		}
		// Retorno si la lista de tipos esta vacia (es decir, que tiene todos los tipos)
		return actualTypes.isEmpty();
	}
}
