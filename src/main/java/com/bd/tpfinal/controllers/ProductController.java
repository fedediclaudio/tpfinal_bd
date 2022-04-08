package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController
{
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    //////  POST

    @PostMapping(value = "/new")
    public void addProduct(@RequestBody Product newProduct)
    {
        this.productService.getAll();
    }

    //////  GET

    @GetMapping("/all")
    public List<Product> getAll()
    {
        return this.productService.getAll();
    }

    @GetMapping("/id/{id}")
    public Optional<Product> getProductById(@PathVariable Long id)
    {
        return this.productService.getProductById(id);
    }
}
