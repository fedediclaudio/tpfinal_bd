package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.AverageProductTypeDto;
import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;

import java.util.List;

public interface ProductRepositoryProxy {
    List<ProductDto> findBySupplierId(String supplierId);

    List<ProductDto> findAllActiveProducts();

    List<AverageProductTypeDto> getAveragePriceProductTypes();

    ProductDto update(String productId, String name, String description, Float weight, Float price, Boolean active) throws PersistenceEntityException;
}