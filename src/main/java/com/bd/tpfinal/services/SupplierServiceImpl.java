package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.SupplierDTO;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getTop10SupplierConMasOrdenesDespachadas() {
       return supplierRepository.getTop10SupplierConMasOrdenesDespachadas();
    }

    @Override
    public List<Supplier> getAllProveedoresByTipo(long id_tipo) {
        return supplierRepository.findAllByTypeId(id_tipo);
    }

    @Override
    public List<Supplier> getProveedoresWithProductosDeTodosLosTipos() {
        return supplierRepository.getProveedoresWithProductosDeTodosLosTipos();
    }
}
