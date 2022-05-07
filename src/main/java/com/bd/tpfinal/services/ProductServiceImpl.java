package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public void newProduct(Product newProduct)
    {
        this.productRepository.save(newProduct);
    }

    @Override
    @Transactional
    public List<Product> getAll()
    {
        return this.productRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Product> getProductById(Long id)
    {
        return this.productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product getProductByName(String name)
    {
        Product producto = null;
        List<Product> productos = this.productRepository.findByName(name);
        if (productos!= null)
            producto = productos.get(0);
        return producto;
    }

}
