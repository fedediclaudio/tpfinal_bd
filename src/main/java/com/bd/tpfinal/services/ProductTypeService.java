package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface ProductTypeService
{
    public void newProductType(ProductType newProductType);
    public List<ProductType> getAll();
    public Optional<ProductType> getProductTypeById(Long id);
    public ProductType getProductTypeByName(String name);
}
