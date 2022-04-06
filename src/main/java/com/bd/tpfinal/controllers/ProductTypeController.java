package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productType")
public class ProductTypeController
{
    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService)
    {
        this.productTypeService = productTypeService;
    }

    //////  POST

    @PostMapping(value="/new")
    public void addProductType(@RequestBody ProductType newProductType)
    {
        this.productTypeService.addProductType(newProductType);
    }

    //////  GET

    @GetMapping("/all")
    public List<ProductType> getAll()
    {
        return this.productTypeService.getAll();
    }

    @GetMapping("/id/{id}")
    public Optional<ProductType> getProductTypeById(@PathVariable Long id)
    {
        return this.productTypeService.getProductTypeById(id);
    }
}
