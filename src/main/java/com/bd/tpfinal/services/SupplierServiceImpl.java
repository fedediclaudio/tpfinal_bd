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
		return supplierRepository.save(supplier);
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

        long productTypes = productTypeRepository.findAll().size();
        List<Supplier> allSuppliers = supplierRepository.findAll();

        allSuppliers.forEach(supplier -> {
            long amount = supplier.getProducts()
                    .stream()
                    .map(Product::getType)
                    .map(ProductType::getName)
                    .distinct()
                    .count();
            if(productTypes == amount){
                offer.add(supplier);
            }
        });

        return offer;
    }
}
