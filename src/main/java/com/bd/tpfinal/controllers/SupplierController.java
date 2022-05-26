package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/suppliers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    // Obtener los diez proveedores que más órdenes despacharon.
    @GetMapping("/top10-suppliers-con-mas-ordenes-despachadas")
    public List<Supplier> getTop10SupplierConMasOrdenesDespachadas() {
        return supplierService.getTop10SupplierConMasOrdenesDespachadas();
    }
}
