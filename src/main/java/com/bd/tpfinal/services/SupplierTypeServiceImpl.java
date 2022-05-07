package com.bd.tpfinal.services;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.SupplierTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public void newSupplierType(SupplierType newSupplierType)
    {
        this.supplierTypeRepository.save(newSupplierType);
    }

    @Override
    @Transactional
    public List<SupplierType> getAll()
    {
        return this.supplierTypeRepository.findAll();
    }

    @Override
    @Transactional
    public SupplierType getSupplierTypeByName(String name)
    {
        List<SupplierType> buscado = this.supplierTypeRepository.findByName(name);
        SupplierType supplierType = null;
        if(buscado != null)
            supplierType = buscado.get(0);
        return supplierType;
    }
}
