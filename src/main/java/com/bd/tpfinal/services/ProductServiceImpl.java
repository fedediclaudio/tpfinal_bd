package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product newProduct)
    {
        this.productRepository.save(newProduct);
    }

    @Override
    public List<Product> getAll()
    {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id)
    {
        return this.productRepository.findById(id);
    }
}
