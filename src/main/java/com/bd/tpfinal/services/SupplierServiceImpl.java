package com.bd.tpfinal.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;
import com.bd.tpfinal.repositories.implementations.SupplierTypeRepository;

@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired SupplierTypeRepository supplierTypeRepository;
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
	
}
