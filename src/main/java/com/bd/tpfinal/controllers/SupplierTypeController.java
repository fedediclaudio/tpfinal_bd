package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.services.SupplierTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplierType")
public class SupplierTypeController
{
    private final SupplierTypeService supplierTypeService;

    @Autowired
    public SupplierTypeController(SupplierTypeService supplierTypeService)
    {
        this.supplierTypeService = supplierTypeService;
    }

    //////  POST

    @PostMapping(value="/new")
    public void addSupplierType(@RequestBody SupplierType newSupplierType)
    {
        this.supplierTypeService.addSupplierType(newSupplierType);
    }

    //////  GET

    @GetMapping("/all")
    public List<SupplierType> getAll()
    {
        return this.supplierTypeService.getAll();
    }

}
