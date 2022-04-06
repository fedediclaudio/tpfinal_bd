package com.bd.tpfinal.services;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeServiceImpl implements ProductTypeService
{
    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository)
    {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public void addProductType(ProductType newProductType)
    {
        this.productTypeRepository.save(newProductType);
    }

    @Override
    public List<ProductType> getAll()
    {
        return this.productTypeRepository.findAll();
    }

    @Override
    public Optional<ProductType> getProductTypeById(Long id)
    {
        return this.productTypeRepository.findById(id);
    }
}
