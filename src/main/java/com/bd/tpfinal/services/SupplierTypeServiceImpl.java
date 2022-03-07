package com.bd.tpfinal.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.implementations.SupplierTypeRepository;

@Service
public class SupplierTypeServiceImpl implements SupplierTypeService {
	@Autowired SupplierTypeRepository supplierTypeRepository;
	
	@Transactional
	public SupplierType saveSupplierType(SupplierType supplierType) throws Exception {
		return supplierTypeRepository.save(supplierType);
	}
	
	public SupplierType createNewSupplierType(SupplierType supplierType) throws Exception {
		// Verifico que tanto el nombre como la descripcion no esten vacíos
		if (supplierType.getName().isBlank() || 
			supplierType.getDescription().isBlank()) 
			return null;
		
		// Busco el SupplierType en la BD
		SupplierType supplierTypeDB = supplierTypeRepository.getSupplierTypeByName(supplierType.getName());
		
		// Si el SupplierType existe, retorno null, ya que estaría como duplicado
		if (supplierTypeDB != null) return null;
		
		// Inicializo el ID
		supplierType.setId(null);
				
		// Grabo el ProductType 
		return saveSupplierType(supplierType);
	}
	
	public List<SupplierType> getSupplierTypeList() throws Exception {
		return supplierTypeRepository.findAll();
	}
	
}
