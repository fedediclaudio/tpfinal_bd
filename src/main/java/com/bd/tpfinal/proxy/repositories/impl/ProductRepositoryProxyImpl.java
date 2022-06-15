package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.AverageProductTypeDto;
import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.exceptions.persistence.EmptyResulsetException;
import com.bd.tpfinal.exceptions.persistence.EntityNotFoundException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.product.ProductMapper;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.proxy.repositories.ProductRepositoryProxy;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import com.bd.tpfinal.repositories.SupplierRepository;

import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryProxyImpl implements ProductRepositoryProxy {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final SupplierRepository supplierRepository;

    private final ProductTypeRepository productTypeRepository;
    private final HistoricalProductPriceRepository historicalProductPriceRepository;

    public ProductRepositoryProxyImpl(ProductRepository productRepository,
                                      ProductMapper productMapper, SupplierRepository supplierRepository,
                                      ProductTypeRepository productTypeRepository,
                                      HistoricalProductPriceRepository historicalProductPriceRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.supplierRepository = supplierRepository;
        this.productTypeRepository = productTypeRepository;
        this.historicalProductPriceRepository = historicalProductPriceRepository;
    }

    @Override
    public List<ProductDto> findBySupplierId(String supplierId) {
        List<Product> products = productRepository.findBySupplier_idAndActive(IdConvertionHelper.convert(supplierId), true);
        return products.stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllActiveProducts() {
        List<Product> products = productRepository.findAllByActive(true);
        return products.stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public List<AverageProductTypeDto> getAveragePriceProductTypes() {
        List<ProductType> types = productTypeRepository.findAll();

        return types.stream().map(type -> {
            float average = type.getProducts().stream().map(prod -> prod.getPrice()).reduce(Float::sum).orElse(0f);
            AverageProductTypeDto dto = new AverageProductTypeDto(type.getId(), type.getName(), average);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override

    public ProductDto update(String productId, String name, String description, Float weight, Float price, Boolean active)  {
        Product product = productRepository
                .findById(IdConvertionHelper.convert(productId))
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found."));
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
                    .stream()
                    .filter(p -> ObjectUtils.isEmpty(p.getFinishDate()))
                    .findFirst()
                    .get();
            if (historicalProductPrice != null)
                historicalProductPrice.setFinishDate(new Date());

            HistoricalProductPrice newHistoricalProductPrice = new HistoricalProductPrice();
            newHistoricalProductPrice.setProduct(product);
            newHistoricalProductPrice.setPrice(price);
            newHistoricalProductPrice.setStartDate(new Date());
            newHistoricalProductPrice = historicalProductPriceRepository.save(newHistoricalProductPrice);
            product.addPrice(newHistoricalProductPrice);
        }
        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto findById(String id) {
        Product product = productRepository.findById(IdConvertionHelper.convert(id)).orElseThrow(()-> new EntityNotFoundException("Can't find product by id: "+id));
        return productMapper.toProductDto(product);
    }

    @Override

    public void delete(String productId) {
        Product product = productRepository.findById(IdConvertionHelper.convert(productId)).orElseThrow(() -> new EntityNotFoundException("Can't find product by id: " + productId));
        product.setActive(false);
        productRepository.save(product);
    }

    @Override

    public ProductDto create(ProductDto dto) {
        Supplier supplier = supplierRepository.findById(IdConvertionHelper.convert(dto.getSupplierId()))
                .orElseThrow(()->new EntityNotFoundException("Can't find supplier with id " + dto.getSupplierId()));

        ProductType productType = productTypeRepository.findById(IdConvertionHelper.convert(dto.getProductTypeId()))
                .orElseThrow(() -> new EntityNotFoundException("Can't find product type with id '" + dto.getProductTypeId() + "'"));

        Product product = new Product();
        product.setActive(true);
        product.setPrice(dto.getPrice());
        product.setWeight(dto.getWeight());
        product.setName(dto.getProductName());
        product.setType(productType);
        supplier.getProducts().add(product);
        product.setDescription(dto.getProductDescription());

        HistoricalProductPrice productPrice = new HistoricalProductPrice();
        productPrice.setProduct(product);
        productPrice.setPrice(dto.getPrice());
        productPrice.setStartDate(new Date());

        product.setSupplier(supplier);
        product.getPrices().add(productPrice);
        product = productRepository.save(product);
        productType.addProduct(product);
        productTypeRepository.save(productType);
        return productMapper.toProductDto(product);

    }

    @Override
    public ProductDto findByIdWithPricesBetweenDates(String productId, Date fromDate, Date toDate) throws EmptyResulsetException {
        List<HistoricalProductPrice> prices = historicalProductPriceRepository
                .findByStartDateGreaterThanAndFinishDateLessThanAndProduct_Id(fromDate, toDate, productId);
        if (prices.isEmpty())
            throw new EmptyResulsetException("No results found for the specified date range");
        Product product = prices.get(0).getProduct();
        product.setPrices(prices);
        return productMapper.toProductDtoWithPrices(product);
    }
}
