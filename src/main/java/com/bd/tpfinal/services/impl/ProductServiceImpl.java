package com.bd.tpfinal.services.impl;

import com.bd.tpfinal.dtos.common.AverageProductTypeDto;
import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.dtos.request.products.CreateProductRequest;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.products.ListProductResponse;
import com.bd.tpfinal.dtos.response.products.SingleProductResponseDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.proxy.repositories.ProductRepositoryProxy;
import com.bd.tpfinal.services.ProductsService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductsService {

    private final ProductRepositoryProxy productRepositoryProxy;

    public ProductServiceImpl(ProductRepositoryProxy productRepositoryProxy) {
        this.productRepositoryProxy = productRepositoryProxy;
    }

    @Override
    public BaseResponseDto update(String productId, CreateProductRequest createProductRequest) {
        SingleProductResponseDto response = new SingleProductResponseDto();
        try {
            ProductDto productDto = productRepositoryProxy.update(productId,
                    createProductRequest.getName(),
                    createProductRequest.getDescription(),
                    createProductRequest.getWeight(),
                    createProductRequest.getPrice(),
                    createProductRequest.isActive());
            response.setData(productDto);
            response.setMessage("Product updated.");
        } catch (PersistenceEntityException e){
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }

    @Override
    public BaseResponseDto getProductsWithProductTypeBySupplier(String supplierId) {
        ListProductResponse response = new ListProductResponse();
        List<ProductDto> products = productRepositoryProxy.findBySupplierId(supplierId);
        response.setMessage("Products from supplier id: " + supplierId);
        response.setData(products);
        return response;
    }

    @Override
    public BaseResponseDto getAverageProductPriceByProductType() {
        List<AverageProductTypeDto> products = productRepositoryProxy.getAveragePriceProductTypes();
        BaseResponseDto response = new ListProductResponse();
        response.setMessage("Average price by product types.");
        response.setData(products);
        return response;
    }

    @Override
    public BaseResponseDto create(String supplierId, CreateProductRequest product) {
        SingleProductResponseDto response = new SingleProductResponseDto();
        ProductDto dto = ProductDto.builder()
                .productName(product.getName())
                .productDescription(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .productTypeId(product.getProductTypeId())
                .supplierId(supplierId)
                .build();

         try {
            dto = productRepositoryProxy.create(dto);
            response.setData(dto);
            response.setMessage("Product created.");
        }catch (PersistenceEntityException e){
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }

    @Override
    public BaseResponseDto<ProductDto> delete(String productId) {
        SingleProductResponseDto response = new SingleProductResponseDto();
        try {
            productRepositoryProxy.delete(productId);
            response.setMessage("Product deleted.");
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }

    @Override
    public BaseResponseDto retrieve() {
        ListProductResponse response = new ListProductResponse();
        response.setMessage("Active products found.");
        List<ProductDto> products = productRepositoryProxy.findAllActiveProducts();
        response.setData(products);
        return response;
    }

    @Override
    public BaseResponseDto retrieve(String productId) {
        SingleProductResponseDto response = new SingleProductResponseDto();
        ProductDto product = null;
        try {
            product = productRepositoryProxy.findById(productId);
            response.setMessage("Product found.");
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        response.setData(product);
        return response;
    }

    @Override
    public BaseResponseDto getProductPriceBetweenDates(String productId, Date fromDate, Date toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SingleProductResponseDto response = new SingleProductResponseDto();
        try {
            ProductDto productDto =  productRepositoryProxy.findByIdWithPricesBetweenDates(productId, fromDate, toDate);
            response.setData(productDto);
            response.setMessage("Product id:" + productId + "with prices between dates [" + sdf.format(fromDate) + ", " + sdf.format(toDate) + "]");
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }
}
