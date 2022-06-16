package com.bd.tpfinal.services;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.implementations.SupplierTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierTypeServiceImpl implements SupplierTypeService {
    @Autowired
    SupplierTypeRepository supplierTypeRepository;


    public SupplierType createNewSupplierType(SupplierType supplierType) throws Exception {

        if (supplierType.getName().isBlank() ||
                supplierType.getDescription().isBlank())
            return null;

        SupplierType supplierTypeDB = supplierTypeRepository.getSupplierTypeByName(supplierType.getName());

        if (supplierTypeDB != null) {
            System.out.println("El SupplierType no existe");
            return null;
        }

        supplierType.setId(null);

        return supplierTypeRepository.save(supplierType);
    }

    public List<SupplierType> getSupplierTypeList() throws Exception {
        return supplierTypeRepository.findAll();
    }

}
