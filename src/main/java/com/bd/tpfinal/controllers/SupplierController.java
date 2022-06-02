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

    // Obtener los diez proveedores que más órdenes despacharon.
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
}
