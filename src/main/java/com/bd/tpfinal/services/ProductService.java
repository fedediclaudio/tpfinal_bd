package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService
{
    public void addProductService(Product newProduct);
    public List<Product> getAll();
    public Optional<Product> getProductById(Long id);
}
