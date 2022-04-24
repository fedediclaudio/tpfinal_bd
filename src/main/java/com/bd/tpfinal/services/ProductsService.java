package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.common.ProductDto;
import com.bd.tpfinal.dtos.request.ProductRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;

import java.util.Date;

public interface ProductsService {

    BaseResponseDto update(String productId, ProductRequestDto productRequestDto);
    BaseResponseDto getProductsWithProductTypeBySupplier(String supplierId);
    BaseResponseDto getAverageProductPriceByProductType();
    BaseResponseDto create(String supplierId, ProductRequestDto product);
    BaseResponseDto delete(String productId);
    BaseResponseDto retrieve();
    BaseResponseDto retrieve(String productId);
    BaseResponseDto  getProductPriceBetweenDates(String productId, Date fromDate, Date toDate);
}
