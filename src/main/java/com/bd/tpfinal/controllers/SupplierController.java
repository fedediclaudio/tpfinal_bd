package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController
{
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService)
    {
        this.supplierService = supplierService;
    }

    //////  POST

    @PostMapping(value = "/new")
    public void addSupplier(@RequestBody Supplier newSupplier)
    {
        this.supplierService.addSupplier(newSupplier);
    }

    //////  GET

    @GetMapping("/all")
    public List<Supplier> getAll()
    {
        return this.supplierService.getAll();
    }

    @GetMapping("/id/{id}")
    public Supplier getSupplierById(@PathVariable Long id)
    {
        return this.supplierService.getSupplierById(id);
    }
}
