package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService
{
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository)
    {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void addSupplier(Supplier newSupplier)
    {
        this.supplierRepository.save(newSupplier);
    }

    @Override
    public List<Supplier> getAll()
    {
        return this.supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(Long id)
    {
        return this.supplierRepository.getById(id);
    }
}
