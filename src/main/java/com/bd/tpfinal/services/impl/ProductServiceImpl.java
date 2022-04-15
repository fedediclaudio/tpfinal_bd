package com.bd.tpfinal.services.impl;

import com.bd.tpfinal.dtos.common.AverageProductTypeDto;
import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.dtos.request.ProductRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.products.ListProductResponseDto;
import com.bd.tpfinal.dtos.response.products.SingleProductResponseDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.proxy.repositories.ProductRepositoryProxy;
import com.bd.tpfinal.services.ProductsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductsService {

    private final ProductRepositoryProxy productRepositoryProxy;

    public ProductServiceImpl(ProductRepositoryProxy productRepositoryProxy) {
        this.productRepositoryProxy = productRepositoryProxy;
    }

    @Override
    public BaseResponseDto update(String productId, ProductRequestDto productRequestDto) {
        SingleProductResponseDto response = new SingleProductResponseDto();
        try {
            ProductDto productDto = productRepositoryProxy.update(productId,
                    productRequestDto.getName(),
                    productRequestDto.getDescription(),
                    productRequestDto.getWeight(),
                    productRequestDto.getPrice(),
                    productRequestDto.isActive());
            response.setData(productDto);

        } catch (PersistenceEntityException e){
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }

    @Override
    public BaseResponseDto getProductsWithProductTypeBySupplier(String supplierId) {
        ListProductResponseDto response = new ListProductResponseDto();
        List<ProductDto> products = productRepositoryProxy.findBySupplierId(supplierId);
        response.setData(products);
        return response;
    }

    @Override
    public BaseResponseDto getAverageProductPriceByProductType() {
        List<AverageProductTypeDto> products = productRepositoryProxy.getAveragePriceProductTypes();
        BaseResponseDto response = new ListProductResponseDto();
        response.setData(products);
        return response;
    }

    @Override
    public BaseResponseDto create(ProductRequestDto product) {
        return null;
    }

    @Override
    public void delete(String productId) {

    }

    @Override
    public BaseResponseDto retrieve() {
        ListProductResponseDto response = new ListProductResponseDto();
        List<ProductDto> products = productRepositoryProxy.findAllActiveProducts();
        response.setData(products);
        return response;
    }

    @Override
    public BaseResponseDto retrieve(String productId) {
        return null;
    }
}
