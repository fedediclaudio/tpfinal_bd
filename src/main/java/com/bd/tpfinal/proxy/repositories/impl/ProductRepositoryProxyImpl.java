package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.AverageProductTypeDto;
import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.product.ProductMapper;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.proxy.repositories.ProductRepositoryProxy;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryProxyImpl implements ProductRepositoryProxy {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductRepositoryProxyImpl(ProductRepository productRepository,
                                      ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> findBySupplierId(String supplierId) {
        List<Product> products = productRepository.findBySupplier_idAndActive(Long.parseLong(supplierId), true);
        return products.parallelStream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllActiveProducts() {
        List<Product> products = productRepository.findAllByActive(true);
        return products.parallelStream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public List<AverageProductTypeDto> getAveragePriceProductTypes() {
        List<Product> products = productRepository.findAveragePriceByProductType();
        return products.parallelStream().map(product -> {
            AverageProductTypeDto dto = new AverageProductTypeDto(product.getType().getId().toString(), product.getType().getName(), product.getPrice());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductDto update(String productId, String name, String description, Float weight, Float price, Boolean active) throws PersistenceEntityException {
        Product product = productRepository
                .findById(Long.parseLong(productId))
                .orElseThrow(() -> new PersistenceEntityException("Product with id " + productId + " not found."));
        if (!ObjectUtils.isEmpty(name))
            product.setName(name);
        if (!ObjectUtils.isEmpty(description))
            product.setDescription(description);
        if (!ObjectUtils.isEmpty(weight) && weight > 0)
            product.setWeight(weight);
        if (active != null)
            product.setActive(active);
        if (!ObjectUtils.isEmpty(price) && price > 0){
            HistoricalProductPrice historicalProductPrice = product.getPrices()
                    .parallelStream()
                    .filter(p -> ObjectUtils.isEmpty(p.getFinishDate()))
                    .findFirst()
                    .get();
            historicalProductPrice.setFinishDate(new Date());
            HistoricalProductPrice newHistoricalProductPrice = new HistoricalProductPrice();
            newHistoricalProductPrice.setProduct(product);
            newHistoricalProductPrice.setPrice(price);
            newHistoricalProductPrice.setStartDate(new Date());
            product.getPrices().add(newHistoricalProductPrice);
            product.setPrice(price);
        }
        productRepository.save(product);
        return productMapper.toProductDto(product);
    }
}
