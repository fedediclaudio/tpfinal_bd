package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.SupplierDTO;
import com.bd.tpfinal.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getTop10SupplierConMasOrdenesDespachadas();
    List<Supplier> getAllProveedoresByTipo(String tipoId);
    List<Supplier> getProveedoresWithProductosDeTodosLosTipos();
    List<SupplierDTO> getAllProveedoresPorCantEstrellas(int cant_estrellas);
}
