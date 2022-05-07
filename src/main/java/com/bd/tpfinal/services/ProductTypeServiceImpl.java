package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public void newProductType(ProductType newProductType)
    {
        String name = newProductType.getName();
        ProductType buscado = getProductTypeByName(name);
        if(buscado == null)
        {
            this.productTypeRepository.save(newProductType);
        }

    }

    @Override
    @Transactional
    public List<ProductType> getAll()
    {
        return this.productTypeRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<ProductType> getProductTypeById(Long id)
    {
        return this.productTypeRepository.findById(id);
    }

    @Override
    @Transactional
    public ProductType getProductTypeByName(String name)
    {
        return this.productTypeRepository.findByName(name);
    }


}
