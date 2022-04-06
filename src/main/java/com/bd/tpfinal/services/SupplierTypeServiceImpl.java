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
    public void addSupplierType(SupplierType newSupplierType)
    {
        this.supplierTypeRepository.save(newSupplierType);
    }

    @Override
    public List<SupplierType> getAll()
    {
        return this.supplierTypeRepository.findAll();
    }
}
