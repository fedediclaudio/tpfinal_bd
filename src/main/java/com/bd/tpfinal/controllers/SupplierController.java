package com.bd.tpfinal.controllers;

import com.bd.tpfinal.DTOs.SupplierDTO;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.services.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/api/suppliers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ModelMapper modelMapper;

    // Obtener los diez proveedor   es que más órdenes despacharon.
    @GetMapping("/top10-suppliers-con-mas-ordenes-despachadas")
    public List<SupplierDTO> getTop10SupplierConMasOrdenesDespachadas() {
        List<Supplier> suppliers = supplierService.getTop10SupplierConMasOrdenesDespachadas();
        return suppliers.stream()
                .map(supplier -> convertToDTO(supplier))
                .collect(Collectors.toList());
    }

    private SupplierDTO convertToDTO(Supplier supplier) {
        SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
        return supplierDTO;
    }
    // Obtener todos los proveedores de un cierto tipo.
    @GetMapping("/proveedores-de-tipo/{id_tipo}")
    public List<SupplierDTO> getAllProveedoresDeUnTipo(@PathVariable long id_tipo) {
        List<Supplier> suppliers = supplierService.getAllProveedoresByTipo(id_tipo);
        return suppliers.stream()
                .map(supplier -> convertToDTO(supplier))
                .collect(Collectors.toList());
    }

    // Obtener proveedores que ofrezcan productos de todos los tipos
    @GetMapping("/proveedores-productos-todos-tipos/")
    public List<SupplierDTO> getProveedoresWithProductosDeTodosLosTipos() {
        List<Supplier> suppliers = supplierService.getProveedoresWithProductosDeTodosLosTipos();
        return suppliers.stream()
                .map(supplier -> convertToDTO(supplier))
                .collect(Collectors.toList());
    }


    @GetMapping("/proveedores-con-estrellas/{cant_estrellas}")
    public List<SupplierDTO> getAllProveedoresPorCantEstrellas(@PathVariable int cant_estrellas) {
        List<SupplierDTO> suppliers = supplierService.getAllProveedoresPorCantEstrellas(cant_estrellas);
        return suppliers;
    }
}