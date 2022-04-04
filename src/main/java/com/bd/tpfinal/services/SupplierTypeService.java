package com.bd.tpfinal.services;

import com.bd.tpfinal.model.SupplierType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierTypeService
{
    public void addSupplierType(SupplierType newSupplierType);
    public List<SupplierType> getAll();

}
