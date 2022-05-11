package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService
{
    public void newSupplier(Supplier newSupplier);
    public List<Supplier> getAll();
    public Supplier getSupplierById(Long id);
    public List<Supplier> getSupplierByName(String name);
    public List<Supplier> getSupplierBySupplierTypeId(Long id);
}
