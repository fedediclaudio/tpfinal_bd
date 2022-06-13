package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.SupplierDTO;
import com.bd.tpfinal.model.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Override
    public List<Supplier> getTop10SupplierConMasOrdenesDespachadas() {
        return null;
    }

    @Override
    public List<Supplier> getAllProveedoresByTipo(long id_tipo) {
        return null;
    }

    @Override
    public List<Supplier> getProveedoresWithProductosDeTodosLosTipos() {
        return null;
    }

    @Override
    public List<SupplierDTO> getAllProveedoresPorCantEstrellas(int cant_estrellas) {
        return null;
    }
}
