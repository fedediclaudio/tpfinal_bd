package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.request.products.CreateProductRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;

import java.util.Date;

public interface ProductsService {

    BaseResponse update(String productId, CreateProductRequest createProductRequest);
    BaseResponse getProductsWithProductTypeBySupplier(String supplierId);
    BaseResponse getAverageProductPriceByProductType();
    BaseResponse create(String supplierId, CreateProductRequest product);
    BaseResponse delete(String productId);
    BaseResponse retrieve();
    BaseResponse retrieve(String productId);
    BaseResponse getProductPriceBetweenDates(String productId, Date fromDate, Date toDate);
}
