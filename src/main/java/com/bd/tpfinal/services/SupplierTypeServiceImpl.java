package com.bd.tpfinal.services;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.SupplierTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierTypeServiceImpl implements SupplierTypeService
{
    private final SupplierTypeRepository supplierTypeRepository;

    @Autowired
    public SupplierTypeServiceImpl(SupplierTypeRepository supplierTypeRepository)
    {
        this.supplierTypeRepository = supplierTypeRepository;
    }

    @Override
    /**
     * No adminte un nuevo SupplierType con nombre ya usado.
     * Lo ignora y no lo salva.
     */
    public void addSupplierType(SupplierType newSupplierType)
    {
        String name = newSupplierType.getName();
        SupplierType buscado = getSupplierTypeByName(name);
        if(buscado==null)
            this.supplierTypeRepository.save(newSupplierType);
    }

    @Override
    public List<SupplierType> getAll()
    {
        return this.supplierTypeRepository.findAll();
    }

    @Override
    public SupplierType getSupplierTypeByName(String name)
    {
        return this.supplierTypeRepository.findByName(name);
    }
}
